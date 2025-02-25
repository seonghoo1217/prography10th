## 적용된 아키텍처

패키지 구조를 보시면 다소 의아한 부분이 드실 수 있다고도 생각합니다.

제가 이번 과제를 진행하며 지향하는 아키텍처는 역할별 계층의 분리였습니다.

그렇기에 `클린 아키텍처`를 보며 영감받은 부분을 적용하였습니다.

Entity와 Repository는 영속성을 띄는 객체와 그를 관리하는 계층은 붙어있어야하듯이요.
또한 **의도적**으로 각 계층을 나타내는 용어인 `Controller` 또는 `Service`를 패키지 네이밍에서 기피하였습니다.

각 패키지가 의도하는 바를 나타낼 수는 있지만 이는 계층을 나타낼 뿐 역할을 나타낸다고 생각하지는 않았습니다.

즉 패키지 구조 또한 일급컬렉션처럼 동작하기를 바랬습니다.

### CQS 패턴 적용

`@Transaction(readonly=true)`를 이용한 영속성 컨텍스트 최적화로 성능 퍼포먼스를 올릴 수 있는 이유도 크지만, 코드의 가독성과 관심사를 분리하고자
`CQS 패턴`을 적용하였습니다.