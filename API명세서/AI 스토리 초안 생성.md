# AI 스토리 초안 생성

카테고리: 스토리
사용자: 유저
Method: POST
URL: /api/v1/story-previews
설명: (AI) 계획과 메모를 분석하여 저장 전 스토리 초안(Preview)을 생성합니다.(표지 생성 옵션 포함)
FE: 시작 전
BE: 시작 전
Auth: Yes
담당자: 강산천

### Request

```jsx
{
  "planId": 101,
  "items": [
    {
      "contentId": 12540, // 해운대
      "userMemo": "바다가 예뻤다",
      "imageUrls": [
        "http://s3.../img_a.jpg",
        "http://s3.../img_b.jpg"
      ]
    }
  ]
}
```

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| planId | 여행 계획 ID | Integer |  | X | 101 |
| style | 문체 스타일 | String | EMOTIONAL, FUNNY... | X | "EMOTIONAL" |
| items | 스토리 항목들 | List | 1개 또는 N개 | X | [{해운대}] or [...] |
| items[].contentId | 관광지 ID | Integer |  | X | 12540 |
| items[].userMemo | 사용자 메모 | String |  | O | "야경이 예뻤음"items[] |
| items[].imageUrls | 업로드된 URL 목록 | List<String> |  | X | `["http://.../a.jpg"]` |

**Query parameter**

### Response

| **key** | **설명** | **value 타입** | **옵션** | **Nullable** | **예시** |
| --- | --- | --- | --- | --- | --- |
| pages | 생성된 페이지들 | List |  | X | [...] |
| pages[].contentId | 관광지 ID | Integer |  | X | 12540 |
| pages[].title | 장소명 | String |  | X | "해운대" |
| pages[].aiText | AI 생성 텍스트 | String |  | X | "해운대의 밤바다는..." |

**Example**

```jsx
{
  "pages": [
    {
      "contentId": 12540,
      "title": "해운대",
      "aiText": "해운대의 밤바다는 별빛보다 더 찬란하게 빛났습니다..."
    }
  ]
}
```

### Status

| status | response content |
| --- | --- |
| 200 | 생성 성공 |
| 400 | 입력값 오류 (사진/메모 없음) |
| 408 | 시간 초과 (AI 응답 지연) |
| 500 | 서버 오류 (AI 연동 실패) |