from flask import Flask, jsonify
import pandas as pd
import numpy as np
import pymysql
from pypfopt.efficient_frontier import EfficientFrontier
from pypfopt import risk_models, expected_returns
from pypfopt.discrete_allocation import DiscreteAllocation, get_latest_prices
import time
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
start_time = 1696377600
assets = [
    "토이케라톱스 공룡장난감 주식회사",
    "스피드킹 자동차 주식회사",
    "마법의 쿠키 과자 주식회사",
    "신비한 해적선 모험 주식회사",
    "꿈꾸는 고양이 카페 주식회사",
]


def makeCountValance(userNo, df):
    conn = pymysql.connect(
        host="j9a308.p.ssafy.io",
        user="root",
        password="1q2w3e4r1!@",
        db="backend",
        charset="utf8",
    )
    cur = conn.cursor()
    now_time = time.time()
    search_time = round(now_time - start_time / 60, 0)
    # 주식 데이터를 불러와서 DataFrame에 넣기 위한 작업
    # api에 주어진 searchTime을 기준으로 모든 주식 데이터를 searchTime 까지 불러옴
    for i in range(1, 6):
        cur.execute(
            f"SELECT now_price FROM stock_data WHERE stock_no = {i} And search_no < {search_time}"
        )
        rows = cur.fetchall()
        prices = [row[0] for row in rows]
        df[assets[i - 1]] = prices

    mu = expected_returns.mean_historical_return(df, frequency=52)
    S = risk_models.sample_cov(df)
    ef = EfficientFrontier(mu, S)

    # 재무상태표에서 자산 확인을 위한 작업
    cur.execute(
        f"SELECT point, deposit, saving, stock FROM balance_sheet WHERE user_no = {userNo}"
    )
    rows = cur.fetchall()
    stock = rows[0][3]
    point = sum(rows[0])
    # 본인 자산 비율에 따라 위험선호도 조정
    # 주식이 없거나 주식비율이 0.3 미만이면 최소 30% 투자를 가정하고 포트폴리오 구성
    if stock == 0 or round(stock / point, 1) < 0.3:
        risk_tolerance = 0.3
        stock = point * 3 // 10
    else:
        risk_tolerance = round(stock / point, 1)
    # 1. 전체 투자 포트폴리오에 대한 최대 변동성 계산
    max_volatility = np.sqrt(np.max(np.diag(S)))

    # 2. risk_tolerance가 최대 변동성을 초과하는 경우 재설정
    if risk_tolerance > max_volatility:
        risk_tolerance = max_volatility
    # 임시저장된 데이터 커밋
    conn.commit()
    # 연결 종료
    conn.close()
    try:
        # 위험선호도에 맞는 포트폴리오 최적화 clean_weights를 활용하여 0에 가까운 값은 0으로 바꾸고 전체를 합쳐서 1이 되게끔 해줌.
        weights = ef.efficient_risk(target_volatility=risk_tolerance)
        cleaned_weights = ef.clean_weights()
        latest_prices = get_latest_prices(df)
        weights = cleaned_weights
        # 현재 내가 가지고 있는 돈까지 입력을 받아서 내게 맞는 포트폴리오 제작시 각 주식을 얼마나 사면 되는지 계산해주고 남는돈까지 계산
        da = DiscreteAllocation(weights, latest_prices, total_portfolio_value=stock)
        allocation, leftover = da.lp_portfolio()

        obj = {
            "ratioBalance": cleaned_weights,
            "moneyBalance": {
                "토이케라톱스 공룡장난감 주식회사": int(allocation.get("토이케라톱스 공룡장난감 주식회사", 0)),
                "스피드킹 자동차 주식회사": int(allocation.get("스피드킹 자동차 주식회사", 0)),
                "마법의 쿠키 과자 주식회사": int(allocation.get("마법의 쿠키 과자 주식회사", 0)),
                "신비한 해적선 모험 주식회사": int(allocation.get("신비한 해적선 모험 주식회사", 0)),
                "꿈꾸는 고양이 카페 주식회사": int(allocation.get("꿈꾸는 고양이 카페 주식회사", 0)),
            },
        }
        return jsonify(obj)
    except Exception as e:
        mu = expected_returns.mean_historical_return(df, frequency=52)
        S = risk_models.sample_cov(df)
        ef = EfficientFrontier(mu, S, solver="COBYLA")
        weights = ef.max_sharpe(risk_free_rate=0)
        cleaned_weights = ef.clean_weights()

        latest_prices = get_latest_prices(df)
        weights = cleaned_weights
        # 현재 내가 가지고 있는 돈까지 입력을 받아서 내게 맞는 포트폴리오 제작시 각 주식을 얼마나 사면 되는지 계산해주고 남는돈까지 계산
        da = DiscreteAllocation(weights, latest_prices, total_portfolio_value=stock)
        allocation, leftover = da.lp_portfolio()

        obj = {
            "ratioBalance": cleaned_weights,
            "moneyBalance": {
                "토이케라톱스 공룡장난감 주식회사": int(allocation.get("토이케라톱스 공룡장난감 주식회사", 0)),
                "스피드킹 자동차 주식회사": int(allocation.get("스피드킹 자동차 주식회사", 0)),
                "마법의 쿠키 과자 주식회사": int(allocation.get("마법의 쿠키 과자 주식회사", 0)),
                "신비한 해적선 모험 주식회사": int(allocation.get("신비한 해적선 모험 주식회사", 0)),
                "꿈꾸는 고양이 카페 주식회사": int(allocation.get("꿈꾸는 고양이 카페 주식회사", 0)),
            },
        }
        return jsonify(obj)


@app.route("/api/v1/portfolio/<int:userNo>")
def makeCount(userNo):
    df = pd.DataFrame()
    return makeCountValance(userNo, df)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=4000, debug=True)
