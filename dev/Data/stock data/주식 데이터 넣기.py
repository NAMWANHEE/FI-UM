import FinanceDataReader as fdr
import pymysql

# 주식 데이터 가져오기("기업번호", "시작일", "종료일")
df = fdr.DataReader("003490", "2020-03-01", "2022-12-31")
# 90일치 주식을 담을 리스트 생성
stock_price = [0] * 703
j = 1
# 주식 담기
for i in df.index:
    stock_price[j] = df.loc[i]["Close"]
    j += 1

# mysql과 연동 (user/password/db 등은 본인것에 맞게 변경하여 사용하기)
conn = pymysql.connect(
    host="j9a308.p.ssafy.io",
    user="root",
    password="1q2w3e4r1!@",
    db="backend",
    charset="utf8",
)

# 삽입
# cur = conn.cursor()
# j = 0
# # 주식 가격 갯수를 기준으로 데이터베이스에 넣기
# tmp1 = 5616
# for k in range(2):
#     for i in range(1, len(stock_price), 1):
#         # 한줄씩 데이터베이스에 넣기
#         tmp = i + tmp1
#         price1 = round(stock_price[i] / 100, 0) * 100
#         price2 = round(stock_price[i - 1] / 100, 0) * 100
#         cur.execute(
#             f"INSERT INTO stock_data VALUES({tmp},{price1-price2},{price1},{j}, 5)"
#         )
#         if j % 540 == 0 and j != 0:
#             j += 900
#         else:
#             j += 2
#     print(tmp)
#     tmp1 += 702

# # 임시저장된 데이터 커밋
# conn.commit()

# # 연결 종료
# conn.close()

# # 수정 001740, 005930, 003550, 000100, 003490
cur = conn.cursor()
j = 0
tmp_j = 0
# 주식 가격 갯수를 기준으로 데이터베이스에 넣기
tmp1 = 5616
for k in range(2):
    for i in range(1, len(stock_price), 1):
        # 한줄씩 데이터베이스에 넣기
        tmp = i + tmp1
        price1 = round(stock_price[i] / 100, 0) * 100
        price2 = round(stock_price[i - 1] / 100, 0) * 100
        diff_price = price1 - price2
        searchtime = j + tmp_j
        cur.execute(
            f"UPDATE stock_data SET fluctuation_price={diff_price}, now_price={price1}, search_no={searchtime}, stock_no=5 WHERE data_no={tmp}"
        )

        if tmp_j % 540 == 0 and tmp_j != 0:
            tmp_j = 0
            j += 1440
        else:
            tmp_j += 2
    print(tmp)
    tmp1 += 702

# 임시저장된 데이터 커밋
conn.commit()

# 연결 종료
conn.close()
