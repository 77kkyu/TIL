# 영속성 컨텍스트

``` text
영속성 컨텍스트는 엔티티를 영구 저장하는 환경이란 뜻입니다
엔티티 매니저를 통하여 컨텍스트에 조회나 저장을 할 수 있고 
애플리케이션과 데이터베이스 사이에서 엔티티를 보관하는 
가상 데이터베이스라고 생각하면 이해가 쉽습니다
```

persist를 통해 영속성 컨텍스트에 저장할 수 있습니다
``` java
entityManager.persist(member);
```

### 영속성 컨텍스트의 특징

- 엔티티 매니저를 통해 영속성 컨텍스트에 접근하고 관리할 수 있습니다
- 영속성 컨텍스트는 식별 값으로 구분을 합니다(식별 값이 꼭 있어야 합니다)
- 영속성 컨텍스트는 보통 트랜잭션의 commit을 하면 데이터베이스에 반영됩니다, 이것을 flush라 합니다
- 영속성 컨텍스트가 엔티티를 관리하면 다음의 장점들이 있습니다
  - 1차 캐시 처리
  - 동일성 보장
  - 트랜잭션 쓰기 지연
  - 변경 감지
  - 지연 로딩

### **_`1차 캐시 처리`_**  

영속성 컨텍스트 내부에 캐시가 있고 이를 1차 캐시라고 부릅니다
데이터베이스의 식별자 값이 1차 캐시의키 입니다 1차 캐시에서 조회방법은 다음과 같습니다
``` java
Member member = entityManager.find(Member.class, "member");
// 엔티티, 식별 키
```
작동 과정 
- 1차 캐시에서 엔티티를 찾습니다
- 컨텍스트에 존재하면 캐싱 처리된 데이터를 바로 가져옵니다
- 존재하지 않으면 데이터베이스와 통신을 합니다
- 데이터베이스에서 가져온 엔티티를 다시 컨텍스트에 저장합니다 
- 컨텍스트에 저장되어 있는 1차 캐시에서 데이터를 꺼내어 반환합니다


### **_`동일성 보장`_**  

엔티티의 동일성을 보장합니다
``` java
Member member1 = entityManager.find(Member.class, "member");
Member member2 = entityManager.find(Member.class, "member");
System.out.print(member1==member2) // true
```


### **_`트랜잭션 쓰기 지연`_**

엔티티 매니저는 커밋이 일어나기 전까지 내부 쿼리 저장소에 INSERT SQL을 가지고 있다
커밋이 일어나는 순간 데이터베이스에 저장이 되는 것을 쓰기 지연이라고 합니다


### **_`변경 감지`_**

JPA로 엔티티를 수정할 때는 단순히 엔티티를 조회해서 데이터를 변경하면 된다

동작 흐름
1. 트랙잭션을 커밋하면 엔티티 매니저 내부에서 먼저 플러시가 호출된다
2. 실제 데이터베이스에 반영하기전에 엔티티와 스냅샷을 비교하여 변경된 엔티티를 찾는다
3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 저장한다
4. 쓰기 지연 저장소의 SQL을 플러시한다
5. 데이터베이스 트랜잭션을 커밋한다

---

### 엔티티의 생명주기
- 비영속(new/transient): 영속성 컨텍스트와 전혀 관계가 없는 상태입니다
    - 엔티티 객체를 생성했지만 아직 영속성 컨텍스트에 저장하지 않은 상태를 비영속(new/transient)이라 한다
    - > Member member = new Member();
- 영속(managed): 영속성 컨텍스트에 저장된 상태입니다
    - 엔티티를 영속성 컨텍스트에 저장한 상태를 말하며 영속성 컨텍스트에 의해 관리된다
    - > entityManager.persist(member);
- 준영속(detached): 영속성 컨텍스트에 저장되었다가 분리된 상태입니다
    - 영속성 컨텍스트가 관리하던 영속 상태의 엔티티 더 이상 관리하지 않으면 준영속 상태가 된다
    - > entityManager.detach(member);
    - > entityManager.claer(); // 영속성 컨텍스트를 초기화함
    - > entityManager.close(); // 영속성 컨텍스트를 종료함
    - 준영속성 상태가 되면 영속성 컨텍스트가 제공하는 1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩을 사용하지 못한다
- 삭제(removed): 삭제된 상태입니다
    - 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제
    - > entityManager.remove(member);

---

### flush
플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다 

쉽게 말해서 트랜잭션의 commit이라고 생각하면 될 것 같다

동작 방식
1. 변경 감지가 동작해서 스냅샷과 비교해서 수정된 엔티티를 찾는다
2. 수정된 엔티티에 대해서 수정 쿼리를 만들거 SQL 저장소에 등록한다
3. 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송한다

사용방법
1. em.flush()
2. 트랙잭션 커밋 자동 호출
3. JPQL 쿼리 실행 자동 호출

---
``` java
@Transactional
@Override
public <S extends T> S save(S entity) {

  Assert.notNull(entity, "Entity must not be null");

  if (entityInformation.isNew(entity)) {
    em.persist(entity);
    return entity;
  } else {
    return em.merge(entity);
  }
}

@Transactional
@Override
public <S extends T> S saveAndFlush(S entity) {

  S result = save(entity);
  flush();

  return result;
}
```

jpa save 구현체에 flush가 없는이유
-> @Transactional에서 commit을 해준다
