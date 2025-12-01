# 📚 EnjoyTrip API 명세서

> **Base URL:** `http://localhost:8080` (Local) / `https://api.enjoytrip.com` (Prod)
> **Auth:** Cookie (`JSESSIONID`)

## 📌 목차
1. [🛠️ 공통 (Common)](#1-️-공통-common)
2. [👤 회원 (Member)](#2--회원-member)
3. [🧩 추천 (Recommendation)](#3--추천-recommendation)
4. [🔍 관광지 (Attraction)](#4--관광지-attraction)
5. [📅 계획 (Plan)](#5--계획-plan)
6. [📖 스토리 (Story)](#6--스토리-story)

---

## 1. 🛠️ 공통 (Common)
시스템 전반에서 사용되는 공통 유틸리티입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/files/upload` | **이미지 파일 업로드** | [보기](./API%20세부%20명세서/이미지%20파일%20업로드.md) |

<br>

## 2. 👤 회원 (Member)
회원 가입, 인증 및 정보 관리 API입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/members` | **회원가입** | [보기](./API%20세부%20명세서/회원가입.md) |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/auth/login` | **로그인** | [보기](./API%20세부%20명세서/로그인.md) |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/auth/logout` | **로그아웃** | [보기](./API%20세부%20명세서/로그아웃.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/members/me` | **회원 정보 조회** | [보기](./API%20세부%20명세서/회원%20정보%20조회.md) |
| ![](https://img.shields.io/badge/PUT-orange?style=flat-square) | `/api/v1/members/me` | **회원 정보 수정** | [보기](./API%20세부%20명세서/회원%20정보%20수정.md) |
| ![](https://img.shields.io/badge/DELETE-red?style=flat-square) | `/api/v1/members/me` | **회원 탈퇴** | [보기](./API%20세부%20명세서/회원%20탈퇴.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/members/availability` | **이메일 중복 검사** | [보기](./API%20세부%20명세서/이메일%20중복%20검사.md) |

<br>

## 3. 🧩 추천 (Recommendation)
사용자 맞춤형 여행지 분석 및 추천 API입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/recommendations/options` | **설문 기초 옵션 조회** | [보기](./API%20세부%20명세서/설문%20기초%20옵션%20조회.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/recommendations/questions/{theme}` | **테마별 상세 질문 조회** | [보기](./API%20세부%20명세서/테마별%20상세%20질문%20조회.md) |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/recommendations` | **맞춤 여행지 추천** | [보기](./API%20세부%20명세서/설문%20기반%20맞춤%20여행지%20추천.md) |

<br>

## 4. 🔍 관광지 (Attraction)
관광지 정보 검색 및 조회 API입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/attractions/search` | **관광지 검색** | [보기](./API%20세부%20명세서/관광지%20검색.md) |

<br>

## 5. 📅 계획 (Plan)
여행 일정 생성 및 관리 API입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/plans` | **여행 계획 생성 및 저장** | [보기](./API%20세부%20명세서/여행%20계획%20생성%20및%20저장.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/plans` | **내 여행 계획 목록** | [보기](./API%20세부%20명세서/내%20여행%20계획%20목록.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/plans/{planId}` | **여행 계획 상세 조회** | [보기](./API%20세부%20명세서/여행%20계획%20상세%20조회.md) |
| ![](https://img.shields.io/badge/PUT-orange?style=flat-square) | `/api/v1/plans/{planId}` | **여행 계획 수정** | [보기](./API%20세부%20명세서/여행%20계획%20수정.md) |
| ![](https://img.shields.io/badge/DELETE-red?style=flat-square) | `/api/v1/plans/{planId}` | **여행 계획 삭제** | [보기](./API%20세부%20명세서/여행%20계획%20삭제.md) |

<br>

## 6. 📖 스토리 (Story)
여행 후기 기록 및 AI 스토리 생성 API입니다.

| Method | URL | 설명 | 상세 |
| :---: | :--- | :--- | :---: |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/story-previews` | **AI 스토리 초안 생성** | [보기](./API%20세부%20명세서/AI%20스토리%20초안%20생성.md) |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/images/transformations` | **AI 이미지 스타일 변환** | [보기](./API%20세부%20명세서/AI%20이미지%20스타일%20변환.md) |
| ![](https://img.shields.io/badge/POST-green?style=flat-square) | `/api/v1/stories` | **스토리북 저장** | [보기](./API%20세부%20명세서/스토리북%20저장.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/stories` | **내 스토리북 목록** | [보기](./API%20세부%20명세서/내%20스토리북%20목록.md) |
| ![](https://img.shields.io/badge/GET-blue?style=flat-square) | `/api/v1/stories/{storyId}` | **스토리북 상세 조회** | [보기](./API%20세부%20명세서/스토리북%20상세%20조회.md) |
| ![](https://img.shields.io/badge/PUT-orange?style=flat-square) | `/api/v1/stories/{storyId}` | **스토리북 수정** | [보기](./API%20세부%20명세서/스토리북%20수정.md) |
| ![](https://img.shields.io/badge/PATCH-yellow?style=flat-square) | `/api/v1/stories/{storyId}/visibility` | **공개 여부 변경** | [보기](./API%20세부%20명세서/공개%20여부%20변경.md) |
| ![](https://img.shields.io/badge/DELETE-red?style=flat-square) | `/api/v1/stories/{storyId}` | **스토리북 삭제** | [보기](./API%20세부%20명세서/스토리북%20삭제.md) |