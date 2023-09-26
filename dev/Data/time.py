import time

# 몇번쨰 주식 보여줄건지
idx = 0
# 시작 시간
start = 12
# 지금 시간
nowmin = 72

add = 1
for i in range(540, 1440, 1):
    if nowmin - start % i == 0:
        add = 0

idx += add


# 현재 시간을 초 단위로 가져오기
current_time_seconds = time.time()
now_time = time.time()
print(current_time_seconds - 41, 200)

print(1694999999 - 400)
