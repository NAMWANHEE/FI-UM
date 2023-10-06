# 📌 JAVA 컨벤션

## 인코딩(ENCODING)
<br>
기본 UTF-8

## 네이밍 (Naming)

1. 변수명 : CamelCase를 기본으로 한다.

```
- userEmail, userId
```

2. 함수명 : CamelCase를 바탕으로 동사로 시작한다.

```
- getUserId(), getItemList()
```

3. 클래스명 : PascalCase를 기본으로 한다.

```
- UserEmail, ItemList
```

4. 컬랙션 : 컬렉션을 명시해준다.

```
- List<Child> childList,
- Map<String, Integer> childMap
```

5. 이중적인 의미를 가지는 단어는 지양한다.

```
- event, design
```

6. URL, 파일명 : kebab-case를 기본으로 한다.

```
- /user-email-page, /item-list
```

7. ENUM, 상수 : 대문자로 네이밍한다.

```
- NORMAL_STATUS, MR_NAM
```

## 프로젝트 구조 (계층형)

```
com
 ㄴ example
     ㄴ sju
         ㄴ config
         ㄴ controller
         ㄴ dto
         ㄴ entity
         ㄴ repository
         ㄴ service
```

## 규칙

1. 콤마 / 구분자 : 콤마(,)와 반복문의 구분자로 쓰이는 세미콜론(;) 뒤에는 공백을 삽입한다.

```
// Good Examples
for (int i = 0; i < length; i++) {
    display(level, message, i)
}

// Bad Examples
for (int i = 0;i < length;i++) {
    display(level,message,i)
}
```

2. 이항/삼항 : 연산자의 앞 뒤에 공백 삽입

```
if (pattern == Access.ABNORMAL) {
    return 0;
}

finalScore += weight * rawScore - absentCount;

if (finalScore > MAX_LIMIT) {
    return MAX_LIMIT;
}
```
