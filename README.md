# Sustainable Voting System MVP

## Что есть
- Регистрация избирателя с проверкой через заглушку Тундук.
- Вход по паспортным данным и паролю.
- Добавление кандидатов и просмотр списка.
- Голосование с запретом повторного голосования.
- Просмотр результатов для трансляции в прямом эфире.

## Эндпоинты (REST)
- POST `/api/auth/register`
- POST `/api/auth/login`
- POST `/api/candidates`
- GET `/api/candidates`
- GET `/api/candidates?district=...`
- POST `/api/votes`
- GET `/api/results?candidateName=...`

## Заглушка Тундук
- Паспорт, начинающийся с `X`, считается не найденным.
- Паспорт, заканчивающийся на `00`, считается возрастом 16.
- В остальных случаях возраст 19 и район `Bishkek`.

## Запуск
1. Подними PostgreSQL и создай БД `sustainable_voting_system`.
2. Проверь `src/main/resources/application.yaml`.
3. Запусти приложение.

## Тесты
- Используется H2 с профилем `test`.

## Pages (Thymeleaf)
- `/` home
- `/register` registration form
- `/login` login form
- `/candidates` candidates list + vote form
- `/results` live results page (polling every second)
- `/admin` admin panel (requires login)
- `/admin/login` admin login form

## Admin credentials
- From `src/main/resources/application.yaml` (app.admin.username / app.admin.password)

## Правило голосования
- Избиратель может голосовать только за кандидата своего района.
