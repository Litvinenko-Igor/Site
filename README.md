
## 📖 About The Project

This is a comprehensive Telegram Bot built with **Java** and **Spring Boot**. The main goal of this project was to practice backend development, integration with third-party APIs (Telegram), and building interactive user interfaces via chat.

The bot allows users to browse different car brands, view specific models, request photos, and get technical characteristics through an interactive menu with inline buttons.

## ✨ Key Features

* **Interactive Menu:** Easy navigation using Telegram Inline Keyboards.
* **Photo Integration:** Sends images of car models directly to the chat (using local resources).
* **Callback Handling:** dynamic responses based on user interaction (e.g., clicking "Characteristics").
* **User Authentication:** Includes a web-interface component for user sign-in (Spring MVC).
* **Robust Error Handling:** System logs errors without crashing the bot conversation.

## 🛠️ Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot (MVC, Core)
* **API:** TelegramBots library
* **Testing:** JUnit 5, Mockito (Unit & Integration tests)
* **Build Tool:** Maven

## 📸 Screenshots

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Litvinenko-Igor/Site.git]
    ```
2.  **Configure the Bot Token:**
    * Create a bot via `@BotFather` in Telegram.
    * Open `src/main/resources/application.properties`.
    * Add your credentials:
        ```properties
        bot.name=AutoSiteIhor_bot
        bot.token=8587202885:AAEGkDZAd8IXxrn5tPTZuJOKsGcB4YDp8-0
        ```
3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

## 🧪 Testing

The project includes unit tests for controllers and service logic. To run tests:

```bash
mvn test
