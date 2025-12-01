# 스토리북 수정

카테고리: 스토리
사용자: 유저
Method: PUT
URL: /api/v1/stories/{storyId}
설명: 이미 저장된 스토리북의 제목이나 특정 페이지의 내용(글/사진)을 수정하여 덮어씁니다.
FE: 시작 전
BE: 시작 전
Auth: Yes
담당자: 강산천

### Request

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| storyId | 스토리 ID | Integer | Path Variable | X | 501 |
| planId | 여행 계획 ID | Integer | 검증용 | X | 101 |
| title | 제목 | String |  | X | "나의 부산 일기 (수정본)" |
| thumbnail | 대표 이미지 URL | String | URL | O | "[http://new-cover](http://new-cover/)..." |
| pages | 페이지 목록 | List |  | X | [...] |
| pages[].contentId | 관광지 ID | Integer |  | X | 12540 |
| pages[].imagePath | 이미지 경로 | String | URL | O | "http://new-image..." |
| pages[].finalText | 최종 텍스트 | String |  | X | "직접 수정한 내용..." |

**Query parameter**

### Response

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| message | 결과 메시지 | String |  | X | "수정 성공" |
| storyId | 스토리 ID | Integer |  | X | 501 |

**Example**

```jsx
{
	"message": "수정 성공",
  "storyId": 501
}
```

### Status

| status | response content |
| --- | --- |
| 200 | 수정 성공 |
| 403 | 권한 없음 (내 글 아님) |
| 404 | 존재하지 않는 스토리 |