# JobPilot

JobPilot is a full-stack job application tracking platform built with Angular and Spring Boot. It helps users manage applications, resumes, interview rounds, analytics, notifications, and AI-assisted career prep from one dashboard.

## Features

- User authentication with JWT-based login and registration
- Job application tracking with status-based workflow
- Interview round management for each application
- Resume upload and storage with Cloudinary
- Analytics dashboard for application insights
- Notifications and unread count support
- Admin dashboard for user and platform stats
- AI tools for resume review, interview question generation, and cover letter generation

## Tech Stack

### Frontend

- Angular 13
- Angular Material
- Chart.js / ng2-charts
- TypeScript

### Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Cloudinary
- Spring Mail

## Project Structure

```text
Jobpilotapp/
├─ frontend/   # Angular client
├─ backend/    # Spring Boot API
└─ README.md
```

## Main Modules

- `Auth`: register, login, current user profile
- `Applications`: create, update, view, and manage job applications
- `Interview Rounds`: track rounds per application
- `Resumes`: upload and manage resumes
- `Analytics`: user-level application metrics
- `Notifications`: reminders and unread counts
- `AI`: resume review, interview prep, and cover letter generation
- `Admin`: user list and admin stats

## API Overview

Base path: `/api/v1`

- `/auth`
- `/applications`
- `/applications/{applicationId}/rounds`
- `/resumes`
- `/analytics`
- `/notifications`
- `/ai`
- `/admin`

## Local Setup

### Prerequisites

- Java 21
- Maven
- Node.js
- npm
- PostgreSQL

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd Jobpilotapp
```

### 2. Configure backend environment variables

Set these before starting the backend:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/jobpilot
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password

JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

APP_FRONTEND_URL=http://localhost:4200

SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your_email
SPRING_MAIL_PASSWORD=your_email_app_password

CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_cloudinary_key
CLOUDINARY_API_SECRET=your_cloudinary_secret

APP_AI_API_KEY=your_openrouter_key
# or
OPENROUTER_API_KEY=your_openrouter_key
APP_AI_MODEL=stepfun/step-3.5-flash:free
APP_AI_API_URL=https://openrouter.ai/api/v1/chat/completions
APP_AI_REFERER=http://localhost:4200
APP_AI_TITLE=JobPilot
```

### 3. Run the backend

```bash
cd backend
./mvnw spring-boot:run
```

Backend default URL:

```text
http://localhost:8080
```

### 4. Run the frontend

```bash
cd frontend
npm install
npm start
```

Frontend default URL:

```text
http://localhost:4200
```

## Build Commands

### Frontend

```bash
cd frontend
npm run build
```

### Backend

```bash
cd backend
./mvnw clean package
```

## Deployment Notes

- The backend is configured to bind to `PORT` for platforms like Render.
- Production frontend API currently points to:

```text
https://jobpilot-dewg.onrender.com
```

- Make sure production environment variables are added in your hosting platform before deployment.

## Security

- JWT is used for authenticated API access
- Protected routes are enforced by Spring Security
- Admin endpoints require admin role access

## Future Improvements

- Add screenshots and demo GIFs
- Add unit and integration test coverage details
- Add Swagger/OpenAPI documentation
- Add CI/CD workflow badges

## Author

Built as a full-stack job tracking and AI career assistant project using Angular and Spring Boot.
