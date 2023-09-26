import re
import pymysql


text_good = """
제목: 피움 나라의 경제성장률 역대 최고!
내용: "피움 나라의 놀라운 경제성장으로 전 세계 투자자들의 관심이 집중되고 있습니다."

제목: 국제 투자자들, 피움 나라 주식에 관심!
내용: "국제적인 경제 회복세에 따라, 투자자들이 피움 나라의 시장에 큰 관심을 보이며 자금이 유입되기 시작했습니다."

제목: 피움 나라 중앙은행, 금리 인하 결정!
내용: "기업들의 자금 조달 비용 감소를 예상하며, 전반적인 경제 활동이 활기를 띨 것으로 보입니다."

제목: 피움 나라, 국제 경제 포럼에서 주목받는 신성장 국가로 선정!
내용: "피움 나라의 경제 안정성과 성장 잠재력이 국제적으로 인정받아 주식 시장에 긍정적인 효과가 예상됩니다."

제목: 원자재 가격 급락, 제조업체들에 호재!
내용: "전 세계적인 원자재 가격의 급락으로 피움 나라의 제조업체들의 원가 절감이 기대됩니다."

"""
text_bad = """

제목: 피움 나라 환율 급락!
내용: "국제 시장에서의 불안정으로 피움 나라의 환율이 급락하며 수출 기업들에게 타격이 예상됩니다."

제목: 피움 나라의 금리 인상 결정!
내용: "중앙은행의 금리 인상 결정으로 기업들의 자금 조달 비용이 증가할 것으로 예상됩니다."

제목: 대규모 파업 운동 확산!
내용: "피움 나라 전역에서 직원들의 대규모 파업 운동이 확산되며 여러 기업들의 영업에 차질이 예상됩니다."

제목: 국제 상품가격 상승에 따른 원자재 비용 급증!
내용: "전 세계적인 상품가격 상승으로 피움 나라의 제조업체들의 원자재 구매 비용이 크게 증가했습니다."

제목: 국제적 투자자들의 피움 나라 투자 회피!
내용: "국제적인 경제 불안정 속에서 투자자들이 피움 나라의 시장을 회피하며, 자금 유출이 관찰되었습니다."
"""


pattern = r'제목: (.*?)\s*내용: "(.*?)"'
matches1 = re.findall(pattern, text_good)
matches2 = re.findall(pattern, text_bad)

# 결과 출력 및 저장
news_list_good = []
for match in matches1:
    title, content = match
    news_list_good.append({"title": title, "content": content})

news_list_bad = []
for match in matches2:
    title, content = match
    news_list_bad.append({"title": title, "content": content})


print(news_list_good)

conn = pymysql.connect(
    host="j9a308.p.ssafy.io",
    user="root",
    password="1q2w3e4r1!@",
    db="backend",
    charset="utf8",
)


cur = conn.cursor()

cur.execute("SELECT * FROM stock_data Where search_no IN (540, 1980, 3420, 4860, 6300)")
rows = cur.fetchall()

# 모든 결과를 가져오기
print(rows)
g = 0
b = 0
tmp_j = 0
tmp = 0
j = 480
for z in range(4):
    over_price = 0
    tmp_z = z
    for i in range(5):
        if rows[tmp_z][2] < rows[tmp_z + 1][2]:
            over_price += 1
        tmp_z += 5

    sql_query = "INSERT INTO stock_news VALUES (%s, %s, %s, 6, %s)"
    if over_price >= 3:
        cur.execute(
            sql_query,
            (
                tmp,
                news_list_good[g].get("content"),
                news_list_good[g].get("title"),
                j,
            ),
        )
        g += 1
    else:
        cur.execute(
            sql_query,
            (
                tmp,
                news_list_bad[b].get("content"),
                news_list_bad[b].get("title"),
                j,
            ),
        )
        b += 1
    j += 1440


# 임시저장된 데이터 커밋
conn.commit()

# 연결 종료
conn.close()
