import re
import pymysql


text_good = """
제목: 스피드킹, '라이트닝' 전기차 출시!
내용: "'라이트닝' 모델로 친환경 차 시장에 발을 딛다."

제목: 스피드킹, 글로벌 디자인 상 수상!
내용: "최신 SUV 모델이 세계적인 디자인 대회에서 인정 받다."

제목: 스피드킹, 해외 시장 진출 성공!
내용: "유럽 시장에서 화려한 데뷔로 이름을 알리다."

제목: 스피드킹, 안전 평가서 최고 점수 획득!
내용: "소비자들의 안전에 대한 신뢰를 증대시키다."

제목: 스피드킹, 친환경 기술 연구소 설립!
내용: "미래를 준비하기 위해 대형 연구소 건립에 나서다."

제목: 스피드킹, 유명 연예인과 광고 계약 체결!
내용: "스타의 브랜드 홍보로 팬들의 관심 집중.

제목: 스피드킹, 여름 특별 프로모션 호평!
내용: "시즌 특별 할인 이벤트로 소비자들의 호응 얻다."

제목: 스피드킹, 자율주행 기술 선도!
내용: "미래를 준비하는 최첨단 기술로 주목받다."

제목: 스피드킹, 대학교와 산학 협력 강화!
내용: "신기술 개발을 위해 국내 주요 대학과 협력 체결."

제목: 스피드킹, 패밀리카 '하모니' 시리즈 확장!
내용: "소비자들의 다양한 선택을 위해 신제품 라인업 추가."

제목: 스피드킹, 서비스 센터 확장 계획!
내용: "고객 만족을 위해 전국 서비스 네트워크를 강화하다."

제목: 스피드킹, 연비 경쟁력 강화!
내용: "에너지 효율 높은 신기술 도입으로 경쟁력을 확보하다."

제목: 스피드킹, 에코 랠리 대회 우승!
내용: "친환경 자동차 대회에서 스피드킹 차량이 빛을 발하다."

제목: 스피드킹, 공유차 서비스에 도전!
내용: "도시형 공유 자동차 사업으로 새로운 시장을 개척하다."

제목: 스피드킹, VIP 전용 모델 선보여!
내용: "프리미엄 라인업 확장으로 고급 시장 공략 나서다."

"""
text_bad = """

제목: 스피드킹, 신제품 런칭 연기
내용: "기술적 이슈로 인해 '스타라이트' 신제품 출시를 연기하다."

제목: 스피드킹, 공장에서 작은 화재 발생
내용: "다행히 인명피해는 없었지만, 생산에 차질이 생길 것으로 예상."

제목: 스피드킹, 유럽 시장에서의 반응 부진
내용: "최신 모델이 유럽 소비자들의 기대를 충족시키지 못하다."

제목: 스피드킹, 주요 부품 공급업체와 갈등
내용: "배터리 공급 문제로 인해 일부 모델의 생산이 지연될 전망."

제목: 스피드킹, 광고 캠페인 논란
내용: "신제품 광고에 대한 소비자들의 부정적인 반응 확산."

제목: 스피드킹, 글로벌 경쟁력 잃어가나?
내용: "글로벌 시장에서의 점유율 감소로 인한 우려가 커지다."

제목: 스피드킹, 서비스 센터 피드백 부진
내용: "소비자들의 서비스 만족도 조사에서 기대 이하의 결과."

제목: 스피드킹, 노조와의 긴밀한 대화 필요성
내용: "임금 및 근무 조건에 대한 노조와의 협상 지속."

제목: 스피드킹, 차세대 모델 개발 지연
내용: "연구 및 개발 단계에서의 난항으로 신제품 출시가 미뤄지다."

제목: 스피드킹, 환경 규제 대응 필요
내용: "글로벌 환경 규제 강화로 인한 대응 전략 재검토 필요."

제목: 스피드킹, 스폰서십 계약 해지 소식
내용: "주요 스포츠 이벤트와의 스폰서십 계약 조기 종료."

제목: 스피드킹, 전기차 배터리 수명 논란
내용: "일부 사용자들의 배터리 수명 관련 불만 사항 제기."

제목: 스피드킹, 해외 판매점 폐쇄
내용: "몇몇 국가에서 판매점을 재조정하는 방향으로 결정."

제목: 스피드킹, 연내 신제품 출시 계획 취소
내용: "예정되었던 연내 신제품 런칭 계획을 잠정 중단하다."

제목: 스피드킹, 자율주행 기술 테스트에 어려움
내용: "최신 자율주행 기술의 안정성 테스트에서 난항을 겪다."
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

cur.execute("SELECT * FROM stock_data Where stock_no = 2")

# 모든 결과를 가져오기
rows = cur.fetchall()
print(rows)
tmp_k = 0
tmp = 16
tmp_no = 0
g = 0
b = 0
tmp_j = 0
time_list = [0, 90, 180, 270]
for z in range(5):
    for k in range(3):
        sql_query = "INSERT INTO stock_news VALUES (%s, %s, %s, 2, %s)"
        print(time_list[k + 1] + tmp_no)
        j = tmp_j + tmp_k
        if rows[time_list[k + 1] + tmp_no][2] > rows[time_list[k] + tmp_no][2]:
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

        if tmp_j % 360 == 0 and tmp_j != 0:
            tmp_j = 0
            tmp_k += 1440
        else:
            tmp_j += 180
        tmp += 1

    tmp_no += 270

# 임시저장된 데이터 커밋
conn.commit()

# 연결 종료
conn.close()
