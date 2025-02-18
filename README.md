# Tom - A Personal Task Manager Chatbot

![Tom Logo](https://your-image-link-here.com)

## 📌 Introduction
Tom is a **task management chatbot** designed to help users keep track of their daily tasks, deadlines, and meetings. It is built with **Java** and follows an **event-driven** approach to ensure seamless user interaction.

## ✨ Features
- ✅ Add **To-Dos**, **Deadlines**, and **Meetings**
- 📅 Supports **date and time parsing**
- ✏️ **Mark, unmark, delete, and list tasks**
- 🔍 **Search** for specific tasks
- 💾 **Data persistence** to save tasks between sessions
- 🖥️ **Command-line interface** for quick task management

## 🚀 Getting Started
### Prerequisites
Ensure you have **Java 17 Azulu** installed.

## 📜 Usage
### Available Commands
| Command | Description |
|---------|-------------|
| `todo TASK_NAME` | Adds a new to-do task |
| `deadline TASK_NAME /by YYYY-MM-DD` | Adds a deadline task |
| `event EVENT_NAME /from YYYY-MM-DD /to YYYY-MM-DD` | Adds a meeting task |
| `list` | Displays all tasks |
| `mark TASK_NUMBER` | Marks a task as done |
| `unmark TASK_NUMBER` | Marks a task as not done |
| `delete TASK_NUMBER` | Deletes a task |
| `find KEYWORD` | Finds tasks containing the keyword |
| `bye` | Exits the chatbot |

## 🛠 Project Structure
```
/ip
│── src/tom
│   ├── Tom.java               # Entry point for the chatbot
│   ├── Chatbot.java           # Handles event processing
│   ├── List.java              # Task list manager
│   ├── Parser.java            # Command parser
│   ├── Ui.java                # Handles user interactions
│   ├── ChatbotDataHandler.java # Manages task storage
│   ├── Events (Folder)
│   │   ├── Event.java
│   │   ├── Greeting.java
│   │   ├── Listen.java
│   │   ├── Exit.java
│   ├── Tasks (Folder)
│   │   ├── Pair.java
│   │   ├── Todo.java
│   │   ├── Deadline.java
│   │   ├── Meeting.java
│── data/Tom.txt                # Stored task data
│── README.md                    # Project documentation
```

## 📂 File Descriptions
- **`Tom.java`** - Main entry point of the chatbot
- **`Chatbot.java`** - Handles chatbot execution flow
- **`Parser.java`** - Parses user commands and executes tasks
- **`List.java`** - Manages task storage and manipulation
- **`ChatbotDataHandler.java`** - Loads and saves tasks to a file
- **`Ui.java`** - Handles user interactions
- **`Pair.java`** - Parent class for all tasks
- **`Todo.java`**, **`Deadline.java`**, **`Meeting.java`** - Task types
- **`Event.java`**, **`Greeting.java`**, **`Listen.java`**, **`Exit.java`** - Event-driven architecture

## 🧪 Running Tests
This project includes **JUnit tests** to ensure reliability.
1. **Compile the tests**:
   ```sh
   javac -d bin -cp .:lib/junit5.jar src/tom/tests/*.java
   ```
2. **Run the tests**:
   ```sh
   java -jar lib/junit-platform-console-standalone.jar --class-path bin --scan-classpath
   ```
---
💡 *Tom: Because task management should be as simple as a conversation.* 🗣️

