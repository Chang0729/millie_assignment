# 쇼핑몰 상품 조회 API

## 1. 프로젝트 개요 및 설계 의도

이 프로젝트는 쇼핑몰의 상품 목록 조회 및 상세 조회를 수행하는 REST API 애플리케이션입니다.  
**헥사고날 아키텍처(Hexagonal Architecture)** 를 기반으로 설계하여 비즈니스 로직의 순수성을 유지하고, 외부 의존성을 격리하여 유지보수성과 확장성을 확보했습니다.

### 설계 원칙 및 의도
- **도메인 모델 보호**: 핵심 비즈니스 로직(가격 계산, 할인 적용, 유효성 검증)은 `Domain` 계층의 `Product` 엔티티 내부에 응집시켰습니다.
- **관심사의 분리**: 
  - **Controller (Adapter)**: HTTP 요청 파싱 및 응답 포맷팅만 담당합니다.
  - **Service (Application)**: 유스케이스 흐름 제어 및 트랜잭션 관리, 도메인 객체 호출을 담당합니다.
  - **Repository (Adapter)**: 데이터베이스 접근 기술을 담당합니다.
- **DTO 사용**: `ProductResponse` DTO를 통해 도메인 모델을 직접 외부로 노출하지 않고, 클라이언트에게 필요한 데이터만 선별하여 전달합니다.

---

## 2. 기술 스택 (Technology Stack)

본 프로젝트는 다음의 기술을 사용하여 개발되었습니다.

| 구분 | 기술           | 버전 / 설명 |
|---|--------------|---|
| **Language** | Java         | 17 (LTS) |
| **Framework** | Spring Boot  | 3.5.8 |
| **Database** | MySQL / 8.0) |

---

## 3. 폴더 구조 및 핵심 코드 설명

### 폴더 구조
```
com.millie.assignment
├── adapter           # 외부 세계와 통신하는 어댑터 계층
│   ├── in.web        # Web Controller (API 진입점)
│   └── out.persistence # JPA Repository (데이터 저장소)
├── application       # 비즈니스 로직 흐름 계층
│   ├── port          # 포트 (인터페이스) 정의
│   │   ├── in        # Incoming Port (UseCase)
│   │   └── out       # Outgoing Port (DB 접근 인터페이스)
│   └── service       # UseCase 구현체
└── domain            # 핵심 비즈니스 로직 및 엔티티
```

### 핵심 코드 구성

#### Domain Layer (`com.millie.assignment.domain`)
- **`Product.java`**: 핵심 도메인 엔티티입니다. 상품의 정가, 할인 정보(유형, 값), 상태 등을 가지며, `calculateProductPrice()`(자체 할인 계산) 및 `calculateFinalPriceWithCoupon()`(쿠폰 적용 계산) 메서드를 통해 비즈니스 규칙을 수행합니다.

#### Application Layer (`com.millie.assignment.application`)
- **`ProductService.java`**: `GetProductUseCase` 인터페이스의 구현체입니다. 상품 리스트 조회 및 단건 조회 요청을 처리하며, 필요에 따라 `Product` 도메인 객체와 협력하여 최종 가격을 산출합니다.
- **`ProductResponse.java`**: API 응답 DTO입니다. `Product` 도메인 객체의 정보를 클라이언트가 사용하기 쉬운 형태로 변환(Mapping)합니다.

#### Adapter Layer (`com.millie.assignment.adapter`)
- **`ProductController.java`**: `/product` 엔드포인트를 통해 클라이언트의 요청을 받고, 적절한 응답을 반환합니다.

---

## 4. API 명세서 (API Specification)

### 4.1 상품 전체 목록 조회
등록된 모든 상품의 목록을 조회합니다.

- **URL**: `GET /product`
- **Description**: 전체 상품 목록을 조회하며, 각 상품의 `finalPrice`는 상품 자체 할인(DiscountType)이 적용된 가격입니다.
- **Response Example**:
```json
[
  {
    "id": 1,
    "name": "오버핏 맨투맨",
    "originalPrice": 35000,
    "finalPrice": 31500,
    "discountType": "PERCENTAGE",
    "value": 10,
    "discountPercent": 10,
    "productStatus": "ON_SALE",
    "isCouponApplicable": true,
    "stockQuantity": 50,
    "saleStartAt": "2024-12-01T10:00:00",
    "saleEndAt": "2025-12-31T23:59:59"
  },
  {
    "id": 2,
    "name": "슬림 슬랙스",
    "originalPrice": 42000,
    "finalPrice": 42000,
    "discountType": null,
    "value": 0,
    "discountPercent": 0,
    "productStatus": "ON_SALE",
    "isCouponApplicable": false,
    "stockQuantity": 30,
    "saleStartAt": "2024-12-01T00:00:00",
    "saleEndAt": "2025-12-31T23:59:59"
  }
]
```

### 4.2 상품 상세 조회
특정 상품의 상세 정보를 조회합니다. 쿠폰 ID를 전달하면 쿠폰 할인이 적용된 최종 예상 가격을 확인할 수 있습니다.

- **URL**: `GET /product/{id}`
- **Query Parameters**:
  - `couponId` (Long, Optional): 적용할 쿠폰의 ID

- **Response Example**:
```json
{
  "id": 1,
  "name": "오버핏 맨투맨",
  "originalPrice": 35000,
  "finalPrice": 28500,
  "discountType": "PERCENTAGE",
  "value": 10,
  "discountPercent": 10,
  "productStatus": "ON_SALE",
  "isCouponApplicable": true,
  "stockQuantity": 50,
  "saleStartAt": "2024-12-01T10:00:00",
  "saleEndAt": "2025-12-31T23:59:59"
}
```
