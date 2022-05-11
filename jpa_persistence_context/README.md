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
- 영속성 컨텍스트는 식별값으로 구분을 합니다(식별값이 꼭 있어야합니다)
- 영속성 컨텍스트는 보통 트랜잭션의 commit을 하면 데이터베이스에 반영됩니다, 이것을 flush라 합니다
- 영속성 컨텍스트가 엔티티를 관리하면 다음의 장점들이 있습니다
  - 1차 캐시처리
  - 동일성 보장 
  - 트랜잭션 쓰기 지연
  - 변경 감지
  - 지연 로딩

1차 캐시처리 - 영속성 컨텍스트 내부에 캐시가 있고 이를 1차 캐시라고 부릅니다
데이터베이스의 식별자 값이 1차 캐시의 키 입니다 1차 캐시에서 조회방법은 다음과 같습니다
``` java
Member member = entityManager.find(Member.class, "member");
// 엔티티, 식별 키
```
작동 과정 
- 1차 캐시에서 엔티티를 찾습니다
- 컨텍스트에 존재하면 캐싱처리된 데이터를 바로 가져옵니다
- 존재하지 않으면 데이터베이스와 통신을 합니다
- 데이터베이스에서 가져온 엔티티를 다시 컨텍스트에 저장합니다 
- 컨텍스트에 저장되어 있는 1차 캐시에서 데이터를 꺼내어 반환합니다


동일성 보장 - 엔티티의 동일성을 보장합니다
``` java
Member member1 = entityManager.find(Member.class, "member");
Member member2 = entityManager.find(Member.class, "member");
System.out.print(member1==member2) // true
```



---

### 엔티티의 생명주기
- 비영속(new/transient): 영속성 컨텍스트와 전혀 관계가 없는 상태입니다
    - 엔티티 객체를 생성했지만 아직 영속성 컨텍스트에 저장하지 않은 상태를 비영속(new/transient)라 한다
    - > Member member = new Member();
- 영속(managed): 영속성 컨텍스트에 저장된 상태입니다
    - 엔티티를 영속성 컨텍스트에 저장한 상태를 말하며 영속성 컨텍스트에 의해 관리된다
    - > entityManager.persist(member);
- 준영속(detached): 영속성 컨텍스트에 저장되었다가 분리된 상태입니다
    - 영속성 컨텍스트가 관리하던 영속 상태의 엔티티 더이상 관리하지 않으면 준영속 상태가 된다
    - > entityManager.detach(member);
    - > entityManager.claer();
    - > entityManager.close();
    - 준영속성 상태가 되면 영속성 컨텍스트가 제공하는 1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩을 사용하지 못한다
- 삭제(removed): 삭제된 상태입니다
    - 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제
    - > entityManager.remove(member);

---



