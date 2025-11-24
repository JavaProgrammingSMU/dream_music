# 꿈 해몽 & 음악 추천 서비스

동양과 서양의 관점에서 꿈을 해석하고, 현재 심리 상태를 분석하며, 꿈에 어울리는 음악을 추천하는 웹 애플리케이션입니다.

---

## 목차

1. [주요 기능](#주요-기능)
2. [기술 스택](#기술-스택)
3. [프로젝트 구조](#프로젝트-구조)
4. [빠른 시작 가이드](#빠른-시작-가이드)
5. [OpenAI API 설정 가이드](#openai-api-설정-가이드)
6. [YouTube Data API 설정 가이드](#youtube-data-api-설정-가이드)
7. [MySQL 데이터베이스 설정 가이드](#mysql-데이터베이스-설정-가이드)
8. [실행 방법](#실행-방법)
9. [API 명세서](#api-명세서)
10. [화면 구성](#화면-구성)
11. [문제 해결](#문제-해결)

---

## 주요 기능

- **회원 관리**: JWT 기반 로그인/회원가입/회원정보 수정/탈퇴
- **꿈 해몽**: OpenAI API를 활용한 동양/서양 관점 해몽 및 심리 분석
- **음악 추천**: 꿈의 분위기에 맞는 음악 추천 및 YouTube 재생
- **꿈 기록 관리**: 이전 꿈 해석 기록 조회 및 삭제

---

## 기술 스택

### Backend
| 기술 | 버전 | 설명 |
|------|------|------|
| Java | 17+ | 프로그래밍 언어 |
| Spring Boot | 3.2.0 | 웹 프레임워크 |
| Spring Security | - | 보안 및 인증 |
| JWT | 0.12.3 | 토큰 기반 인증 |
| Spring Data JPA | - | ORM |
| H2 Database | - | 개발용 인메모리 DB |
| MySQL | 8.0+ | 운영용 데이터베이스 |
| WebFlux | - | OpenAI API 비동기 호출 |
| Lombok | - | 보일러플레이트 코드 제거 |

### Frontend
| 기술 | 버전 | 설명 |
|------|------|------|
| React | 18.2.0 | UI 라이브러리 |
| React Router DOM | 6.21.0 | 라우팅 |
| Axios | 1.6.2 | HTTP 클라이언트 |
| React Icons | 4.12.0 | 아이콘 라이브러리 |

---

## 프로젝트 구조

```
dream_music_project/
├── backend/
│   ├── pom.xml                              # Maven 설정
│   └── src/main/
│       ├── java/com/dreammusic/
│       │   ├── DreamMusicApplication.java   # 메인 애플리케이션
│       │   ├── config/
│       │   │   ├── SecurityConfig.java      # Spring Security 설정
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── controller/
│       │   │   ├── AuthController.java      # 인증 API
│       │   │   ├── UserController.java      # 사용자 API
│       │   │   └── DreamController.java     # 꿈 해몽 API
│       │   ├── dto/                         # 요청/응답 DTO
│       │   ├── entity/
│       │   │   ├── User.java                # 사용자 엔티티
│       │   │   └── DreamRecord.java         # 꿈 기록 엔티티
│       │   ├── repository/                  # JPA Repository
│       │   ├── security/
│       │   │   ├── JwtTokenProvider.java    # JWT 토큰 생성/검증
│       │   │   ├── JwtAuthenticationFilter.java
│       │   │   └── CustomUserDetailsService.java
│       │   └── service/
│       │       ├── AuthService.java         # 인증 서비스
│       │       ├── UserService.java         # 사용자 서비스
│       │       ├── DreamService.java        # 꿈 해몽 서비스
│       │       ├── OpenAIService.java       # OpenAI API 연동
│       │       └── YouTubeService.java      # YouTube 검색
│       └── resources/
│           └── application.yml              # 애플리케이션 설정
│
├── frontend/
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── index.js                         # 엔트리 포인트
│       ├── App.js                           # 라우팅 설정
│       ├── components/
│       │   ├── Header.js                    # 상단 헤더
│       │   ├── Sidebar.js                   # 사이드바 (꿈 히스토리)
│       │   ├── DreamResult.js               # 해몽 결과 표시
│       │   └── YouTubePlayer.js             # 음악 플레이어
│       ├── pages/
│       │   ├── LoginPage.js                 # 로그인 페이지
│       │   ├── SignupPage.js                # 회원가입 페이지
│       │   ├── MainPage.js                  # 메인 페이지
│       │   └── ProfilePage.js               # 프로필 페이지
│       ├── context/
│       │   └── AuthContext.js               # 인증 상태 관리
│       ├── services/
│       │   └── api.js                       # API 호출 모듈
│       └── styles/                          # CSS 스타일
│
└── README.md
```

---

## 빠른 시작 가이드

> 5분 안에 프로젝트를 실행하는 방법입니다.

### 사전 요구사항

- **Java 17+** 설치
- **Node.js 18+** 설치
- **Maven 3.6+** 설치
- **OpenAI API Key** (필수)
- **YouTube Data API Key** (필수)

### Step 1: API 키 준비

1. **OpenAI API Key**: [OpenAI Platform](https://platform.openai.com/api-keys)에서 발급
2. **YouTube API Key**: [Google Cloud Console](https://console.cloud.google.com/)에서 발급

### Step 2: 환경 설정

`backend/src/main/resources/application.yml` 파일에서 API 키 설정:

```yaml
openai:
  api:
    key: sk-proj-your-openai-api-key-here  # OpenAI API 키 입력

youtube:
  api:
    key: AIzaSy-your-youtube-api-key-here   # YouTube API 키 입력
```

또는 환경변수로 설정:

**Windows (CMD)**
```cmd
set OPENAI_API_KEY=sk-proj-your-openai-api-key
set YOUTUBE_API_KEY=AIzaSy-your-youtube-api-key
```

**Windows (PowerShell)**
```powershell
$env:OPENAI_API_KEY="sk-proj-your-openai-api-key"
$env:YOUTUBE_API_KEY="AIzaSy-your-youtube-api-key"
```

**Mac/Linux**
```bash
export OPENAI_API_KEY=sk-proj-your-openai-api-key
export YOUTUBE_API_KEY=AIzaSy-your-youtube-api-key
```

### Step 3: 백엔드 실행

```bash
cd backend
mvn spring-boot:run
```

서버가 시작되면: http://localhost:8080

### Step 4: 프론트엔드 실행

새 터미널을 열고:

```bash
cd frontend
npm install
npm start
```

브라우저에서: http://localhost:3000

### Step 5: 사용하기

1. 회원가입 후 로그인
2. 꿈 내용 입력
3. 동양/서양 해몽 및 심리 분석 확인
4. 추천 음악 감상

---

## OpenAI API 설정 가이드

### 1단계: OpenAI 계정 생성

1. [OpenAI 웹사이트](https://platform.openai.com/) 접속
2. 우측 상단 **Sign Up** 클릭하여 계정 생성
3. 이메일 인증 완료

### 2단계: API Key 발급

1. 로그인 후 [API Keys 페이지](https://platform.openai.com/api-keys) 접속
2. **+ Create new secret key** 버튼 클릭
3. Key 이름 입력 (예: "dream-music-app")
4. **Create secret key** 클릭
5. 생성된 API Key 복사 (⚠️ 이 키는 한 번만 표시되므로 안전한 곳에 저장)

```
예시: sk-proj-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### 3단계: 결제 설정 (필수)

1. [Billing 페이지](https://platform.openai.com/account/billing) 접속
2. **Add payment method** 클릭
3. 신용카드 정보 입력
4. **Set up paid account** 완료

> ⚠️ **주의**: 무료 크레딧이 만료되면 API 호출이 실패합니다. 최소 $5 이상 충전을 권장합니다.

### 4단계: API Key 적용 방법

#### 방법 1: 환경변수 설정 (권장)

**Windows (CMD)**
```cmd
set OPENAI_API_KEY=sk-proj-your-actual-api-key-here
```

**Windows (PowerShell)**
```powershell
$env:OPENAI_API_KEY="sk-proj-your-actual-api-key-here"
```

**Mac/Linux**
```bash
export OPENAI_API_KEY=sk-proj-your-actual-api-key-here
```

**영구 설정 (Windows)**
1. 시스템 속성 → 고급 → 환경 변수
2. 사용자 변수에서 **새로 만들기** 클릭
3. 변수 이름: `OPENAI_API_KEY`
4. 변수 값: `sk-proj-your-actual-api-key-here`

#### 방법 2: application.yml 직접 수정

```yaml
# backend/src/main/resources/application.yml
openai:
  api:
    key: sk-proj-your-actual-api-key-here  # 실제 키로 변경
    url: https://api.openai.com/v1/chat/completions
    model: gpt-3.5-turbo  # 또는 gpt-4 (비용 더 높음)
```

> ⚠️ **보안 경고**: application.yml에 직접 입력 시 Git에 커밋하지 않도록 주의하세요!

### API 사용량 및 비용

| 모델 | 입력 (1K tokens) | 출력 (1K tokens) |
|------|------------------|------------------|
| gpt-3.5-turbo | $0.0005 | $0.0015 |
| gpt-4 | $0.03 | $0.06 |
| gpt-4-turbo | $0.01 | $0.03 |

> 꿈 해몽 1회당 약 $0.01~$0.05 예상 (gpt-3.5-turbo 기준)

---

## YouTube Data API 설정 가이드

YouTube Data API v3를 사용하여 꿈에 어울리는 음악을 검색하고 추천합니다.

### 1단계: Google Cloud 프로젝트 생성

1. [Google Cloud Console](https://console.cloud.google.com/) 접속
2. Google 계정으로 로그인
3. 상단의 프로젝트 선택 드롭다운 클릭
4. **새 프로젝트** 클릭
5. 프로젝트 이름 입력 (예: "dream-music-app")
6. **만들기** 클릭

### 2단계: YouTube Data API v3 활성화

1. 좌측 메뉴에서 **API 및 서비스** → **라이브러리** 클릭
2. 검색창에 "YouTube Data API v3" 입력
3. **YouTube Data API v3** 선택
4. **사용** 버튼 클릭

### 3단계: API Key 발급

1. 좌측 메뉴에서 **API 및 서비스** → **사용자 인증 정보** 클릭
2. 상단의 **+ 사용자 인증 정보 만들기** 클릭
3. **API 키** 선택
4. 생성된 API 키 복사 (⚠️ 안전한 곳에 저장)

```
예시: AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### 4단계: API Key 제한 설정 (권장)

보안을 위해 API 키 사용을 제한하는 것이 좋습니다:

1. 생성된 API 키 옆의 **수정** 아이콘 클릭
2. **애플리케이션 제한사항**에서:
   - 개발 중: **없음** 선택
   - 운영 시: **IP 주소** 선택 후 서버 IP 입력
3. **API 제한사항**에서:
   - **키 제한** 선택
   - **YouTube Data API v3** 체크
4. **저장** 클릭

### 5단계: API Key 적용 방법

#### 방법 1: application.yml 직접 수정

```yaml
# backend/src/main/resources/application.yml
youtube:
  api:
    key: AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  # 실제 키로 변경
```

#### 방법 2: 환경변수 설정 (권장)

**Windows (CMD)**
```cmd
set YOUTUBE_API_KEY=AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

**Windows (PowerShell)**
```powershell
$env:YOUTUBE_API_KEY="AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
```

**Mac/Linux**
```bash
export YOUTUBE_API_KEY=AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

**영구 설정 (Windows)**
1. 시스템 속성 → 고급 → 환경 변수
2. 사용자 변수에서 **새로 만들기** 클릭
3. 변수 이름: `YOUTUBE_API_KEY`
4. 변수 값: `AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`

### API 무료 할당량

| 항목 | 할당량 |
|------|--------|
| 일일 무료 할당량 | 10,000 유닛 |
| 검색 요청 1회 | 100 유닛 소모 |
| 일일 검색 가능 횟수 | 약 100회 |

> 개인 프로젝트나 소규모 사용에는 무료 할당량으로 충분합니다.

### 할당량 초과 시

- 할당량이 초과되면 다음 날 자정(태평양 시간) 기준으로 초기화됩니다.
- 더 많은 할당량이 필요한 경우 Google Cloud에서 결제 설정 후 추가 할당량을 요청할 수 있습니다.

---

## MySQL 데이터베이스 설정 가이드

### 1단계: MySQL 설치

#### Windows
1. [MySQL 다운로드 페이지](https://dev.mysql.com/downloads/mysql/) 접속
2. **MySQL Installer for Windows** 다운로드
3. 설치 시 **Developer Default** 또는 **Server only** 선택
4. Root 비밀번호 설정 (기억해두세요!)

#### Mac (Homebrew)
```bash
brew install mysql
brew services start mysql
mysql_secure_installation  # 보안 설정
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

### 2단계: 데이터베이스 생성

MySQL에 접속:
```bash
# Windows
mysql -u root -p

# Mac/Linux
sudo mysql -u root -p
```

데이터베이스 및 사용자 생성:
```sql
-- 데이터베이스 생성
CREATE DATABASE dreamdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 사용자 생성 (운영 환경용)
CREATE USER 'dreamuser'@'localhost' IDENTIFIED BY 'your_password_here';

-- 권한 부여
GRANT ALL PRIVILEGES ON dreamdb.* TO 'dreamuser'@'localhost';

-- 변경사항 적용
FLUSH PRIVILEGES;

-- 확인
SHOW DATABASES;
```

### 3단계: application.yml 수정

`backend/src/main/resources/application.yml` 파일을 다음과 같이 수정:

```yaml
server:
  port: 8080

spring:
  # ============================================
  # H2 데이터베이스 설정 (개발용) - 주석 처리
  # ============================================
  # datasource:
  #   url: jdbc:h2:mem:dreamdb
  #   driver-class-name: org.h2.Driver
  #   username: sa
  #   password:
  # h2:
  #   console:
  #     enabled: true
  #     path: /h2-console

  # ============================================
  # MySQL 데이터베이스 설정 (운영용)
  # ============================================
  datasource:
    url: jdbc:mysql://localhost:3306/dreamdb?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: dreamuser          # 생성한 사용자명
    password: your_password_here # 생성한 비밀번호

  jpa:
    hibernate:
      ddl-auto: update           # 운영: validate, 개발: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect

jwt:
  secret: your-256-bit-secret-key-for-jwt-authentication-dream-music-app-2024
  expiration: 86400000  # 24시간 (밀리초)

openai:
  api:
    key: ${OPENAI_API_KEY:your-openai-api-key}
    url: https://api.openai.com/v1/chat/completions
    model: gpt-3.5-turbo

logging:
  level:
    com.dreammusic: DEBUG
    org.hibernate.SQL: DEBUG
```

### 4단계: MySQL 연결 테스트

```bash
cd backend
mvn spring-boot:run
```

정상 실행 시 콘솔에 다음과 유사한 메시지가 표시됩니다:
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
Hibernate: create table users ...
Hibernate: create table dream_records ...
```

### 테이블 구조 확인

```sql
USE dreamdb;
SHOW TABLES;
DESCRIBE users;
DESCRIBE dream_records;
```

**users 테이블:**
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT | PK, Auto Increment |
| email | VARCHAR(255) | 이메일 (Unique) |
| password | VARCHAR(255) | 암호화된 비밀번호 |
| nickname | VARCHAR(255) | 닉네임 |
| created_at | DATETIME | 생성일시 |
| updated_at | DATETIME | 수정일시 |

**dream_records 테이블:**
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT | PK, Auto Increment |
| user_id | BIGINT | FK → users.id |
| dream_content | TEXT | 꿈 내용 |
| eastern_interpretation | TEXT | 동양 해몽 |
| western_interpretation | TEXT | 서양 해몽 |
| psychological_analysis | TEXT | 심리 분석 |
| recommended_music_title | VARCHAR(255) | 추천 음악 제목 |
| recommended_music_artist | VARCHAR(255) | 아티스트 |
| youtube_video_id | VARCHAR(255) | YouTube 영상 ID |
| created_at | DATETIME | 생성일시 |

---

## 실행 방법

### 사전 요구사항

- Java 17 이상
- Node.js 18 이상
- Maven 3.6 이상
- MySQL 8.0 이상 (운영용, 선택사항)
- OpenAI API Key (필수)
- YouTube Data API Key (필수)

### Backend 실행

```bash
# 1. 프로젝트 디렉토리로 이동
cd dream_music_project/backend

# 2. API Key 환경변수 설정

# Windows CMD
set OPENAI_API_KEY=sk-proj-your-openai-api-key
set YOUTUBE_API_KEY=AIzaSy-your-youtube-api-key

# Windows PowerShell
$env:OPENAI_API_KEY="sk-proj-your-openai-api-key"
$env:YOUTUBE_API_KEY="AIzaSy-your-youtube-api-key"

# Mac/Linux
export OPENAI_API_KEY=sk-proj-your-openai-api-key
export YOUTUBE_API_KEY=AIzaSy-your-youtube-api-key

# 3. 애플리케이션 실행
mvn spring-boot:run

# 또는 JAR 파일로 빌드 후 실행
mvn clean package -DskipTests
java -jar target/dream-music-api-1.0.0.jar
```

> 환경변수 대신 `application.yml`에 직접 API 키를 입력해도 됩니다.

백엔드 서버: http://localhost:8080

### Frontend 실행

```bash
# 1. 프로젝트 디렉토리로 이동
cd dream_music_project/frontend

# 2. 의존성 설치
npm install

# 3. 개발 서버 실행
npm start

# 또는 프로덕션 빌드
npm run build
```

프론트엔드 서버: http://localhost:3000

### 동시 실행 (Windows)

두 개의 터미널을 열어 각각 실행하거나, 아래와 같이 백그라운드 실행:

**터미널 1 - Backend**
```bash
cd backend
mvn spring-boot:run
```

**터미널 2 - Frontend**
```bash
cd frontend
npm start
```

### 실행 확인

1. 백엔드: http://localhost:8080 접속 시 에러 없이 응답
2. 프론트엔드: http://localhost:3000 접속 시 로그인 페이지 표시
3. H2 Console (개발용 DB): http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:dreamdb`
   - Username: `sa`
   - Password: (빈칸)

---

## API 명세서

### Base URL
```
http://localhost:8080/api
```

### 공통 응답 형식

**성공 응답**
```json
{
  "data": { ... }
}
```

**에러 응답**
```json
{
  "error": "에러 메시지"
}
```

---

### 1. 인증 API (Authentication)

#### 1.1 회원가입

| 항목 | 내용 |
|------|------|
| **URL** | `POST /api/auth/signup` |
| **인증** | 불필요 |
| **설명** | 새로운 사용자 계정을 생성합니다. |

**Request Body**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "nickname": "홍길동"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| email | String | ✅ | 이메일 형식 |
| password | String | ✅ | 최소 6자 이상 |
| nickname | String | ✅ | 2~20자 |

**Response (200 OK)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "user@example.com",
  "nickname": "홍길동",
  "userId": 1
}
```

**Error Response (400 Bad Request)**
```json
{
  "error": "Email already exists"
}
```

---

#### 1.2 로그인

| 항목 | 내용 |
|------|------|
| **URL** | `POST /api/auth/login` |
| **인증** | 불필요 |
| **설명** | 기존 사용자 로그인 및 JWT 토큰 발급 |

**Request Body**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "user@example.com",
  "nickname": "홍길동",
  "userId": 1
}
```

**Error Response (401 Unauthorized)**
```json
{
  "error": "Invalid email or password"
}
```

---

### 2. 사용자 API (User)

> ⚠️ 모든 사용자 API는 인증이 필요합니다.

**인증 헤더**
```
Authorization: Bearer {JWT_TOKEN}
```

---

#### 2.1 현재 사용자 정보 조회

| 항목 | 내용 |
|------|------|
| **URL** | `GET /api/users/me` |
| **인증** | ✅ 필요 |
| **설명** | 로그인한 사용자의 정보를 조회합니다. |

**Response (200 OK)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "nickname": "홍길동",
  "createdAt": "2024-01-15T10:30:00"
}
```

---

#### 2.2 사용자 정보 수정

| 항목 | 내용 |
|------|------|
| **URL** | `PUT /api/users/me` |
| **인증** | ✅ 필요 |
| **설명** | 닉네임 또는 비밀번호를 변경합니다. |

**Request Body**
```json
{
  "nickname": "새닉네임",
  "currentPassword": "현재비밀번호",
  "password": "새비밀번호"
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| nickname | String | ❌ | 변경할 닉네임 (2~20자) |
| currentPassword | String | 조건부 | 비밀번호 변경 시 필수 |
| password | String | ❌ | 새 비밀번호 (최소 6자) |

**Response (200 OK)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "nickname": "새닉네임",
  "createdAt": "2024-01-15T10:30:00"
}
```

**Error Response (400 Bad Request)**
```json
{
  "error": "Current password is incorrect"
}
```

---

#### 2.3 회원 탈퇴

| 항목 | 내용 |
|------|------|
| **URL** | `DELETE /api/users/me` |
| **인증** | ✅ 필요 |
| **설명** | 계정을 삭제합니다. 모든 꿈 기록도 함께 삭제됩니다. |

**Request Body**
```json
{
  "password": "현재비밀번호"
}
```

**Response (200 OK)**
```json
{
  "message": "User deleted successfully"
}
```

---

### 3. 꿈 해몽 API (Dream)

> ⚠️ 모든 꿈 해몽 API는 인증이 필요합니다.

---

#### 3.1 꿈 해석 요청

| 항목 | 내용 |
|------|------|
| **URL** | `POST /api/dreams/interpret` |
| **인증** | ✅ 필요 |
| **설명** | 꿈 내용을 입력받아 해몽 결과와 음악 추천을 반환합니다. |

**Request Body**
```json
{
  "dreamContent": "어젯밤 꿈에서 하늘을 날고 있었습니다. 구름 위를 자유롭게 날아다니며 아래를 내려다보니 도시가 작게 보였습니다. 기분이 매우 좋았습니다."
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| dreamContent | String | ✅ | 꿈 내용 (최소 1자 이상) |

**Response (200 OK)**
```json
{
  "id": 1,
  "dreamContent": "어젯밤 꿈에서 하늘을 날고 있었습니다...",
  "easternInterpretation": "동양의 관점에서 하늘을 나는 꿈은 길몽입니다. 자유와 해방을 상징하며, 가까운 미래에 승진이나 좋은 소식이 있을 수 있습니다...",
  "westernInterpretation": "프로이트의 관점에서 비행은 성적 욕망의 상징일 수 있습니다. 융의 분석에 따르면, 이는 자아 실현과 정신적 성장을 나타냅니다...",
  "psychologicalAnalysis": "현재 당신은 삶에서 더 큰 자유와 가능성을 갈망하고 있는 것으로 보입니다. 스트레스에서 벗어나고 싶은 욕구가 꿈에 반영되었습니다...",
  "recommendedMusicTitle": "A Sky Full of Stars",
  "recommendedMusicArtist": "Coldplay",
  "youtubeVideoId": "VPRjCeoBqrI",
  "createdAt": "2024-01-15T10:30:00"
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 꿈 기록 ID |
| dreamContent | String | 입력한 꿈 내용 |
| easternInterpretation | String | 동양 관점 해몽 |
| westernInterpretation | String | 서양 관점 해몽 |
| psychologicalAnalysis | String | 심리 상태 분석 |
| recommendedMusicTitle | String | 추천 음악 제목 |
| recommendedMusicArtist | String | 아티스트명 |
| youtubeVideoId | String | YouTube 영상 ID |
| createdAt | DateTime | 생성 일시 |

---

#### 3.2 꿈 기록 목록 조회

| 항목 | 내용 |
|------|------|
| **URL** | `GET /api/dreams/history` |
| **인증** | ✅ 필요 |
| **설명** | 사용자의 모든 꿈 기록을 최신순으로 조회합니다. |

**Response (200 OK)**
```json
[
  {
    "id": 2,
    "dreamContent": "두 번째 꿈 내용...",
    "easternInterpretation": "...",
    "westernInterpretation": "...",
    "psychologicalAnalysis": "...",
    "recommendedMusicTitle": "...",
    "recommendedMusicArtist": "...",
    "youtubeVideoId": "...",
    "createdAt": "2024-01-16T08:00:00"
  },
  {
    "id": 1,
    "dreamContent": "첫 번째 꿈 내용...",
    "easternInterpretation": "...",
    "westernInterpretation": "...",
    "psychologicalAnalysis": "...",
    "recommendedMusicTitle": "...",
    "recommendedMusicArtist": "...",
    "youtubeVideoId": "...",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

---

#### 3.3 특정 꿈 기록 조회

| 항목 | 내용 |
|------|------|
| **URL** | `GET /api/dreams/{id}` |
| **인증** | ✅ 필요 |
| **설명** | 특정 ID의 꿈 기록을 조회합니다. |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 꿈 기록 ID |

**Response (200 OK)**
```json
{
  "id": 1,
  "dreamContent": "꿈 내용...",
  "easternInterpretation": "...",
  "westernInterpretation": "...",
  "psychologicalAnalysis": "...",
  "recommendedMusicTitle": "...",
  "recommendedMusicArtist": "...",
  "youtubeVideoId": "...",
  "createdAt": "2024-01-15T10:30:00"
}
```

**Error Response (400 Bad Request)**
```json
{
  "error": "Dream record not found"
}
```

---

#### 3.4 꿈 기록 삭제

| 항목 | 내용 |
|------|------|
| **URL** | `DELETE /api/dreams/{id}` |
| **인증** | ✅ 필요 |
| **설명** | 특정 꿈 기록을 삭제합니다. |

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 꿈 기록 ID |

**Response (200 OK)**
```json
{
  "message": "Dream record deleted successfully"
}
```

**Error Response (400 Bad Request)**
```json
{
  "error": "Unauthorized access to dream record"
}
```

---

### API 요약 테이블

| 메서드 | 엔드포인트 | 인증 | 설명 |
|--------|------------|------|------|
| POST | `/api/auth/signup` | ❌ | 회원가입 |
| POST | `/api/auth/login` | ❌ | 로그인 |
| GET | `/api/users/me` | ✅ | 내 정보 조회 |
| PUT | `/api/users/me` | ✅ | 내 정보 수정 |
| DELETE | `/api/users/me` | ✅ | 회원 탈퇴 |
| POST | `/api/dreams/interpret` | ✅ | 꿈 해석 요청 |
| GET | `/api/dreams/history` | ✅ | 꿈 기록 목록 |
| GET | `/api/dreams/{id}` | ✅ | 특정 꿈 조회 |
| DELETE | `/api/dreams/{id}` | ✅ | 꿈 기록 삭제 |

---

## Frontend API 호출 예시

### api.js 서비스 모듈

```javascript
// frontend/src/services/api.js

import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: { 'Content-Type': 'application/json' },
});

// 요청 인터셉터: JWT 토큰 자동 첨부
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 인증 API
export const authAPI = {
  signup: (data) => api.post('/auth/signup', data),
  login: (data) => api.post('/auth/login', data),
};

// 사용자 API
export const userAPI = {
  getMe: () => api.get('/users/me'),
  updateMe: (data) => api.put('/users/me', data),
  deleteMe: (password) => api.delete('/users/me', { data: { password } }),
};

// 꿈 해몽 API
export const dreamAPI = {
  interpret: (dreamContent) => api.post('/dreams/interpret', { dreamContent }),
  getHistory: () => api.get('/dreams/history'),
  getById: (id) => api.get(`/dreams/${id}`),
  delete: (id) => api.delete(`/dreams/${id}`),
};
```

### 사용 예시

```javascript
// 로그인
const response = await authAPI.login({
  email: 'user@example.com',
  password: 'password123'
});
localStorage.setItem('token', response.data.token);

// 꿈 해석 요청
const dream = await dreamAPI.interpret('어젯밤 바다에서 수영하는 꿈을 꿨습니다.');
console.log(dream.data.easternInterpretation);

// 히스토리 조회
const history = await dreamAPI.getHistory();
console.log(history.data);
```

---

## 화면 구성

### 1. 로그인/회원가입 페이지
- 이메일, 비밀번호 입력
- 달과 별 애니메이션 배경
- 회원가입 ↔ 로그인 전환

### 2. 메인 페이지
```
┌─────────────────────────────────────────────────────────────┐
│  [≡] 꿈 해몽                              홍길동  [👤] [🚪] │
├─────────┬───────────────────────────────────┬───────────────┤
│         │                                   │               │
│ 사이드바 │         꿈 입력 영역              │   YouTube     │
│         │  ┌─────────────────────────────┐  │   플레이어    │
│ • 꿈 #1  │  │ 어젯밤 꾼 꿈을 입력하세요... │  │               │
│ • 꿈 #2  │  └─────────────────────────────┘ │  🎵 추천 음악  │
│ • 꿈 #3  │                                  │  - 제목       │
│         │  [동양해몽] [서양해몽] [심리분석]   │  - 아티스트   │
│         │  ┌─────────────────────────────┐  │               │
│         │  │                             │  │  [▶ YouTube]  │
│         │  │    해몽 결과 표시 영역       │  │               │
│         │  │                             │  │               │
│         │  └─────────────────────────────┘  │               │
└─────────┴───────────────────────────────────┴───────────────┘
```

### 3. 프로필 페이지
- 기본 정보 (이메일, 닉네임)
- 비밀번호 변경
- 회원 탈퇴 (위험 구역)

---

## 문제 해결

### OpenAI API 오류

**401 Unauthorized**
- API 키가 잘못되었거나 만료됨
- 해결: 새 API 키 발급

**429 Too Many Requests**
- 요청 한도 초과
- 해결: 잠시 후 재시도 또는 결제 플랜 업그레이드

**500 Internal Server Error**
- OpenAI 서버 문제
- 해결: 잠시 후 재시도

### YouTube API 오류

**403 Forbidden - Daily Limit Exceeded**
```
에러: quotaExceeded
```
- 일일 할당량(10,000 유닛) 초과
- 해결: 다음 날까지 대기 또는 Google Cloud에서 할당량 증가 요청

**403 Forbidden - YouTube Data API has not been enabled**
```
에러: accessNotConfigured
```
- YouTube Data API v3가 활성화되지 않음
- 해결: Google Cloud Console에서 YouTube Data API v3 활성화

**400 Bad Request - API key not valid**
```
에러: badRequest
```
- API 키가 잘못됨
- 해결: Google Cloud Console에서 새 API 키 발급

**음악 추천이 안 됨 (youtubeVideoId가 null)**
- YouTube API 키가 설정되지 않았거나 잘못됨
- 해결 방법:
  1. `application.yml`에서 `youtube.api.key` 확인
  2. 백엔드 재시작 필요 (코드 변경 후)
  3. 콘솔 로그에서 "Failed to search YouTube video" 에러 확인

### MySQL 연결 오류

**Access denied for user**
```
해결: 사용자명/비밀번호 확인
mysql -u root -p
ALTER USER 'dreamuser'@'localhost' IDENTIFIED BY 'new_password';
```

**Unknown database 'dreamdb'**
```
해결: 데이터베이스 생성
CREATE DATABASE dreamdb;
```

### CORS 오류

프론트엔드에서 백엔드 호출 시 CORS 오류 발생:
```
해결: SecurityConfig.java에서 allowedOrigins 확인
configuration.setAllowedOrigins(List.of("http://localhost:3000"));
```

### 백엔드가 실행되지 않음

**Port 8080 already in use**
```bash
# Windows - 포트 사용 중인 프로세스 확인
netstat -ano | findstr :8080

# 프로세스 종료
taskkill /PID <PID번호> /F
```

**Maven 빌드 실패**
```bash
# 의존성 재설치
mvn clean install -DskipTests
```

### 프론트엔드가 실행되지 않음

**Port 3000 already in use**
```bash
# Windows - 포트 사용 중인 프로세스 확인
netstat -ano | findstr :3000

# 프로세스 종료
taskkill /PID <PID번호> /F
```

**node_modules 문제**
```bash
# node_modules 삭제 후 재설치
rm -rf node_modules
npm install
```

---

## 라이선스

MIT License
