from flask import Flask, render_template_string, jsonify
import pandas as pd
import numpy as np
import pymysql
from pypfopt.efficient_frontier import EfficientFrontier
from pypfopt import risk_models, expected_returns
from pypfopt.discrete_allocation import DiscreteAllocation, get_latest_prices
import time


app = Flask(__name__)
start_time = 1695600000
assets = ["공", "스피드킹", "마법쿠키", "해적선", "고양이"]

def makeCountValance(userNo):
    conn = pymysql.connect(
        host="j9a308.p.ssafy.io",
        user="root",
        password="1q2w3e4r1!@",
        db="backend",
        charset="utf8",
    )
    try:
        df = pd.DataFrame()
        cur = conn.cursor()
        now_time = time.time()
        search_time = now_time - start_time / 60
        # 주식 데이터를 불러와서 DataFrame에 넣기 위한 작업
        # api에 주어진 searchTime을 기준으로 모든 주식 데이터를 searchTime 까지 불러옴
        for i in range(1, 6):
            cur.execute(
                f"SELECT now_price FROM stock_data WHERE stock_no = {i} And search_no < {search_time}"
            )
            rows = cur.fetchall()
            prices = [row[0] for row in rows]
            df[assets[i - 1]] = prices

        mu = expected_returns.mean_historical_return(df)
        S = risk_models.sample_cov(df)
        ef = EfficientFrontier(mu, S)
        print(df)

        # 재무상태표에서 자산 확인을 위한 작업
        cur.execute(
            f"SELECT point, deposit, saving, stock FROM balance_sheet WHERE user_no = {userNo}"
        )
        rows = cur.fetchall()
        stock = rows[0][3]
        point = sum(rows[0])
        # 본인 자산 비율에 따라 위험선호도 조정
        risk_tolerance = round(stock / point, 1)

        # 위험선호도에 맞는 포트폴리오 최적화 clean_weights를 활용하여 0에 가까운 값은 0으로 바꾸고 전체를 합쳐서 1이 되게끔 해줌.
        weights = ef.efficient_risk(target_volatility=risk_tolerance)
        cleaned_weights = ef.clean_weights()

        # 임시저장된 데이터 커밋
        conn.commit()
        # 연결 종료
        conn.close()
        latest_prices = get_latest_prices(df)
        weights = cleaned_weights
        # 현재 내가 가지고 있는 돈까지 입력을 받아서 내게 맞는 포트폴리오 제작시 각 주식을 얼마나 사면 되는지 계산해주고 남는돈까지 계산
        da = DiscreteAllocation(weights, latest_prices, total_portfolio_value=stock)
        allocation, leftover = da.lp_portfolio()

        obj = {
            "ratioBalance": cleaned_weights,
            "moneyBalance": {
                "공룡": int(allocation.get("공", 0)),
                "스피드킹": int(allocation.get("스피드킹", 0)),
                "마법쿠키": int(allocation.get("마법쿠키", 0)),
                "해적선": int(allocation.get("해적선", 0)),
                "고양이": int(allocation.get("고양이", 0)),
            },
        }
        return jsonify(obj)
    except Exception as e:
        return jsonify({"error": str(e)})


@app.route("/api/v1/<int:userNo>")
def makeCount(userNo):
    return makeCountValance(userNo)


if __name__ == "__main__":
    app.run(debug=True)
