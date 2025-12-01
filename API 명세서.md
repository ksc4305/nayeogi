# 📚 나여기 API 명세서

## 📌 목차 (Quick Links)
1. [🛠️ 공통 (Common)](#1-🛠️-공통-common)
2. [👤 회원 (Member)](#2-👤-회원-member)
3. [🧩 추천 (Recommendation)](#3-🧩-추천-recommendation)
4. [🔍 관광지 (Attraction)](#4-🔍-관광지-attraction)
5. [📅 계획 (Plan)](#5-📅-계획-plan)
6. [📖 스토리 (Story)](#6-📖-스토리-story)

---
## 1. 🛠️ 공통 (Common)
시스템 전반에서 사용되는 공통 기능입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **이미지 파일 업로드** | `POST` | `/api/v1/files/upload` | [보기](./API%20세부%20명세서/이미지%20파일%20업로드.md) |

<br>

## 2. 👤 회원 (Member)
회원 가입, 인증, 정보 관리를 위한 API입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **회원가입** | `POST` | `/api/v1/members` | [보기](./API%20세부%20명세서/회원가입.md) |
| **로그인** | `POST` | `/api/v1/auth/login` | [보기](./API%20세부%20명세서/로그인.md) |
| **로그아웃** | `POST` | `/api/v1/auth/logout` | [보기](./API%20세부%20명세서/로그아웃.md) |
| **회원 정보 조회** | `GET` | `/api/v1/members/me` | [보기](./API%20세부%20명세서/회원%20정보%20조회.md) |
| **회원 정보 수정** | `PUT` | `/api/v1/members/me` | [보기](./API%20세부%20명세서/회원%20정보%20수정.md) |
| **회원 탈퇴** | `DELETE` | `/api/v1/members/me` | [보기](./API%20세부%20명세서/회원%20탈퇴.md) |
| **이메일 중복 검사** | `GET` | `/api/v1/members/availability` | [보기](./API%20세부%20명세서/이메일%20중복%20검사.md) |

<br>

## 3. 🧩 추천 (Recommendation)
사용자 맞춤형 여행지 추천을 위한 설문 및 분석 API입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **설문 기초 옵션 조회** | `GET` | `/api/v1/recommendations/options` | [보기](./API%20세부%20명세서/설문%20기초%20옵션%20조회.md) |
| **테마별 상세 질문 조회** | `GET` | `/api/v1/recommendations/questions/{theme}` | [보기](./API%20세부%20명세서/테마별%20상세%20질문%20조회.md) |
| **맞춤 여행지 추천** | `POST` | `/api/v1/recommendations` | [보기](./API%20세부%20명세서/설문%20기반%20맞춤%20여행지%20추천.md) |

<br>

## 4. 🔍 관광지 (Attraction)
관광지 정보 검색 및 조회를 위한 API입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **관광지 검색** | `GET` | `/api/v1/attractions/search` | [보기](./API%20세부%20명세서/관광지%20검색.md) |

<br>

## 5. 📅 계획 (Plan)
여행 일정을 생성하고 관리하는 핵심 API입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **여행 계획 생성 및 저장** | `POST` | `/api/v1/plans` | [보기](./API%20세부%20명세서/여행%20계획%20생성%20및%20저장.md) |
| **내 여행 계획 목록** | `GET` | `/api/v1/plans` | [보기](./API%20세부%20명세서/내%20여행%20계획%20목록.md) |
| **여행 계획 상세 조회** | `GET` | `/api/v1/plans/{planId}` | [보기](./API%20세부%20명세서/여행%20계획%20상세%20조회.md) |
| **여행 계획 수정** | `PUT` | `/api/v1/plans/{planId}` | [보기](./API%20세부%20명세서/여행%20계획%20수정.md) |
| **여행 계획 삭제** | `DELETE` | `/api/v1/plans/{planId}` | [보기](./API%20세부%20명세서/여행%20계획%20삭제.md) |

<br>

## 6. 📖 스토리 (Story)
여행 후기를 기록하고 AI로 생성하는 API입니다.

| 기능 | Method | URL | 상세 문서 |
| :--- | :---: | :--- | :---: |
| **AI 스토리 초안 생성** | `POST` | `/api/v1/story-previews` | [보기](./API%20세부%20명세서/AI%20스토리%20초안%20생성.md) |
| **AI 이미지 스타일 변환** | `POST` | `/api/v1/images/transformations` | [보기](./API%20세부%20명세서/AI%20이미지%20스타일%20변환.md) |
| **스토리북 저장** | `POST` | `/api/v1/stories` | [보기](./API%20세부%20명세서/스토리북%20저장.md) |
| **내 스토리북 목록** | `GET` | `/api/v1/stories` | [보기](./API%20세부%20명세서/내%20스토리북%20목록.md) |
| **스토리북 상세 조회** | `GET` | `/api/v1/stories/{storyId}` | [보기](./API%20세부%20명세서/스토리북%20상세%20조회.md) |
| **스토리북 수정** | `PUT` | `/api/v1/stories/{storyId}` | [보기](./API%20세부%20명세서/스토리북%20수정.md) |
| **공개 여부 변경** | `PATCH` | `/api/v1/stories/{storyId}/visibility` | [보기](./API%20세부%20명세서/공개%20여부%20변경.md) |
| **스토리북 삭제** | `DELETE` | `/api/v1/stories/{storyId}` | [보기](./API%20세부%20명세서/스토리북%20삭제.md) |