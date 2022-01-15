# Immutable, Mutable

> Immutable : 변경할 수 없는, 불변의
> 
> Mutable : 변할 수 있는, 잘 변하는

직역하면 이렇다

``` java
String name = "해쉬";
System.out.print(name);
name = "해쉬브라운";
System.out.print(name);
```

처음에는 단순하게 name이 처음 메모리에 할당되고

name의 메모리 주소를 참조해서 값을 변경하니까 Mutable인가? 생각했습니다

하지만 찾아보고 테스트한 결과 메모리 사용하는 방식이 다르다는 걸 알았습니다...

<br>

---

## Immutable : String (concat)
- 문자열 연산에서 new 연산을 통해 생성된 객체의 메모리 주소는 변하지 않는다
- 문자열에 변화를 주면 메모리 주소를 참조해서 값을 변경하는 것이 아닌 새로운 String 객체를 새로 만들어 메모리에 할당한다
- 기존의 문자열은 Garbage Collection에서 제거한다
- 문자열 연산이 적고 멀티쓰레드 환경일 경우

``` java
String name = "해쉬"; // GC에서 제거
name = name.concat(" 입니다"); // new String() 
```

<br>

---

## Mutable : StringBuilder, StringBuffer
- 문자열 연산에서 객체를 한번 만든 후에 메모리 주소의 값을 변경시킨다
- 문자열 연산이 자주 있을 경우 사용한다
- StringBuilder : 문자열 연산이 많고 단일쓰레드 이거나 동기화를 고려하지 않아도 되는 경우
- StringBuffer : 문자열 연산이 많고 멀티쓰레드 환경일 경우

``` java
StringBuilder builder = new StringBuilder();
builder.append("해쉬");
builder.append(" 입니다");
```

### StringBuilder
- 동기화를 지원하지 않는다
- 싱글 쓰레드 환경이거나 쓰레드를 신경 쓰지 않아도 되는 상황
- 문자열 연산이 많고 단일쓰레드 이거나 동기화를 고려하지 않아도 되는 경우

### StringBuffer
- 동기화를 지원하여 멀티쓰레드 환경에서 안전하다
- 문자열 연산이 많고 멀티쓰레드 환경일 경우