# OneBoard

21년 2학기 SW 캡스톤디자인 수업 OneBoard 프로젝트의 백엔드 서버 repository 입니다.

## 개요

### 교육용 화상회의 플랫폼 Oneboard

[시연 영상](https://www.youtube.com/watch?v=wQyvDcriDIc)

#### 문제점
- 비대면 수업에서 강의자의 학생 이해정도와 학습상황 등 파악의 어려움
- 화상회의 플랫폼과 학습관리 시스템의 분리로 인한 불편

#### 솔루션
- 강의자와 학생 간의 원활한 상호작용을 위한 기능 제공
- 화상회의 플랫폼과 학습관리 시스템의 통합

## 기능

### 강의자

- 비대면 수업 기능
  - 음성 / 비디오 / 화면 공유 및 채팅
  - 비대면 수업 중 이해도 체크
  - 비대면 수업 중 퀴즈 출제
  - 비대면 수업 중 출석 체크
- 학습관리 기능
  - 과목 및 수업 관리
  - 과제 / 시험 및 성적 관리
  - 학생 출석 관리

### 학생

- 비대면 수업 기능
  - 음성 / 비디오 / 화면 공유 및 채팅
  - 비대면 수업 중 졸음 감지
- 학습관리 기능
  - 과목 및 수업 정보 확인
  - 과제 / 시험 및 성적 정보 확인
  - 본인 출석 정보 확인

## 사용 기술 및 환경

Java (JDK 11), Spring Boot (v2.5.5), Junit5, MySQL (v5.7.36), Socket.io, Naver Cloud Platform, IntelliJ, TablePlus, Postman

## 테이블 구조

<img width="800" alt="ERD" src="https://user-images.githubusercontent.com/41173401/171562696-1aee5a86-ca9d-4d43-afd4-59caabdf34c6.png">

## API

```
// 성공 시
{
	"result": "SUCCESS",
	"data": // Response data
}

// 실패 시
{
	"result": "FAIL",
	"data": null
}
```
