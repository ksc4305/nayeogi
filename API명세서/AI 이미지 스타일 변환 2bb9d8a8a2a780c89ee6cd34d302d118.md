# AI 이미지 스타일 변환

카테고리: 스토리
사용자: 유저
Method: POST
URL: /api/v1/images/transformations
설명: 사용자가 업로드한 사진을 지정된 화풍(애니메이션, 수채화 등)으로 변환하여 새로운 이미지 URL을 반환합니다.
기타: AI 처리 시간(약 5~10초) 소요
FE: 시작 전
BE: 시작 전
Auth: Yes
담당자: 강산천

### Request

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| originalUrl | 원본 이미지 URL | String |  | X | "http://s3.../photo.jpg" |
| style | 변환할 스타일 | String |  | X | "ANIME" |

**Query parameter**

### Response

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| transformedUrl | 변환된 이미지 URL | String |  | X | "http://s3.../anime_ver.jpg" |

**Example**

```jsx
{
	"transformedUrl": "http://domain.com/images/anime_photo.jpg"
}
```

### Status

| status | response content |
| --- | --- |
| 200 | 변환 성공 |
| 400 | 지원하지 않는 스타일 |
| 500 | AI 서버 연동 실패 |