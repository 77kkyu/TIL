# Description

# 낙관적 잠금 (Optimisstic Lock)
   
낙관적 락은 트랜잭션 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법입니다.
데이터베이스에서 제공하는 락을 거는 방식이 아닌 JPA에서 제공하는 버전 관리 기능을 사용합니다.
JPA에서 Entity내부에 @Version Annotation을 사용하여 변수를 구현합니다

> 실질적으로 잠금이라기보다는 충돌 감지(Conflict detection)에 가깝습니다

> Version을 적용할 수 있는 타입
> - int
> - Integer
> - short
> - Short
> - long
> - Long
> - java.sql.Timestamp

엔티티에 Version을 최초로 생성하면 0이 되고 그 후로 업데이트를 할 때마다
1씩 증가합니다. 
1. A트랜잭션에서 Member테이블에서 id = 0 인 데이터를 조회합니다, 이때 Version은 0입니다.
2. B트랜잭션에서도 Member테이블에서 id = 0 인 데이터를 조회합니다, 이때 Version은 0입니다.
3. A트랜잭션에서 해당 데이터를 업데이트했을 시 Version은 1이 됩니다. 이때 커밋하기 전에 Version이 0인지 체크를 합니다
4. B트랜잭션에서도 조회한 데이터를 업데이트를 합니다. 이때 B트랜잭션에서 처음 조회했을 때는 Version이 0 이였지만 
    커밋하기 전에 다시 한번 Version을 체크해 Version이 1인걸 확인하면 예외를 발생합니다.
5. OptimisticLockException 발생

---

# 비관적 잠금 (Pessimistic Lock)

트랜잭션이 충돌한다고 가정하고 데이터베이스에 제공하는 락을 미리 거는 방식입니다.

비관적 잠금의 LockModeType

- PESSIMISTIC_READ
  - 반복 읽기만 가능하고 Update나 Delete가 되는 것을 방지한다.
  - 공유 잠금
- PESSIMISTIC_WRITE
  - READ, UPDATE, DELETE 하는 것을 방지합니다
  - 배타적 잠금 
- PESSIMISTIC_FORCE_INCREMENT
  - Version 정보를 사용하는 비관적 락

---

# 암시적 잠금 (Implicit Lock)

JPA에서 제공하는 엔티티 필드에 @Version 또는 @OptimisticLocking 어노테이션이 설정되어있을 경우에는
자동적으로 충돌 감지를 위한 잠금이 실행됩니다. 그리고 업데이트, 삭제 쿼리가 발생 시에
암시적으로 해당 로우에 대한 행 배타 잠금(Row Exclusive Lock)이 실행됩니다.

---

# 명시적 잠금 (Explicit Lock)

프로그램을 통해 의도적으로 잠금을 실행하는 것이 명시적 잠금입니다. 
JPA에서 엔터티를 조회할 때 entityManager를 사용하여 LockMode를 지정하거나 
select for update 쿼리를 통해서 직접 잠금을 지정할 수 있습니다.

    