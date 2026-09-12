# Nourishing Life (JSF + Hibernate)

A Java rewrite of the "Nourishing Life" health/nutrition app (originally React + Firebase),
built with **JSF 2.3** (Mojarra) and **Hibernate ORM** on **PostgreSQL**, for the WebTech
CRUD assignment.

## Requirements

- JDK 17+
- Maven 3.6+
- PostgreSQL running locally (a Windows service such as `postgresql-x64-17` is fine)

## 1. Create the database

Once, from a terminal (or via pgAdmin):

```sql
CREATE DATABASE nourishing_life;
```

Then set your Postgres username/password in
`src/main/resources/hibernate.cfg.xml` (`hibernate.connection.username` /
`hibernate.connection.password`). Tables are created/updated automatically on
startup (`hibernate.hbm2ddl.auto=update`) — no manual schema scripts needed.

## 2. Run it

From the project root:

```bash
mvn jetty:run
```

Then open **http://localhost:8081/**. Stop with `Ctrl+C`.

### Running from IntelliJ

Either:
- Open the project as a Maven project, and run the `jetty:run` Maven goal from the Maven tool window (Maven > Plugins > jetty > jetty:run), **or**
- Configure a local Tomcat 9 server run configuration deploying the `nourishing-life` WAR artifact (`mvn package` produces `target/nourishing-life.war`).

Note: `mvn tomcat7:run` (the `tomcat7-maven-plugin`) is intentionally *not* used here —
it bundles a 2013-era embedded Tomcat 7 that throws a classloader `LinkageError`
against EL 3.0 on modern JDKs. `jetty:run` is the supported one-command way to
run this project.

## Default admin login

On first startup, a default admin account is seeded automatically:

- **Email:** `health@gmail.com`
- **Password:** `Admin@123`

Any other email registered through Sign Up becomes a regular `USER`.

## What's implemented

- Full page flow: sign up / sign in, disease browsing (cancer / diabetes /
  hypertension) with 6 meal categories each, meal detail, FAQ, About, star
  rating + feedback, per-user Settings (dark mode, notifications, medicine
  reminder, profile edit, password change).
- **Admin dashboard**: stats, a pure-CSS bar chart (system overview) and a
  pure-CSS pie chart (feedback status, via an inline `conic-gradient`),
  recent feedback.
- **Full CRUD (JSF + Hibernate)** on two entities:
  - **User** — `admin/users.xhtml` (add / list / edit / delete)
  - **Feedback** — `admin/feedback.xhtml` (list / reply=update / edit / delete);
    replying to a feedback entry auto-creates an FAQ entry, mirroring the
    original app's behavior.
- **All 3 required validation types**, on the sign-up, settings and rating
  forms:
  1. JSF standard validators (`f:validateLength`)
  2. Bean Validation / JSR-303 annotations (`@NotBlank`, `@Email`, `@Size`, `@Min`/`@Max`)
  3. Custom `javax.faces.validator.Validator` classes (`PhoneNumberValidator`, `PasswordMatchValidator`)
- **All 3 required CSS types**:
  1. External — `resources/css/styles.css`
  2. Internal — `<style>` block in `admin/dashboard.xhtml`
  3. Inline — computed `style="..."` attributes (chart bars, pie chart, status badges)
- Server-side enforcement of admin-only routes via `AuthFilter` (the original
  React app only hid the admin nav link client-side — this closes that gap).

## Project layout

```
src/main/java/com/nourishinglife/
  model/       JPA entities: User, Feedback, Faq (+ Role, FeedbackStatus enums)
  data/        Static seed data: Disease, Meal, MealCategory, DiseaseData
  dao/         Hibernate DAOs: UserDAO, FeedbackDAO, FaqDAO
  bean/        JSF managed beans (one per page/feature)
  validator/   Custom JSF Validators
  filter/      AuthFilter (session + admin-role enforcement)
  listener/    AppStartupListener (seeds the admin account)
  util/        HibernateUtil, PasswordUtil
src/main/resources/hibernate.cfg.xml
src/main/webapp/                Facelets pages (template.xhtml + one .xhtml per route)
src/main/webapp/admin/          Admin-only pages
src/main/webapp/resources/css/  External stylesheet
```
