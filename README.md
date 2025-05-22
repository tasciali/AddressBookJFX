# 📒 JavaFX Address Book

This is a simple **Address Book** desktop application built using JavaFX. It features user login and file-based storage to manage personal address records in a lightweight and intuitive GUI.

## 🧩 Features

- JavaFX-based desktop interface
- Username and password login system
- File-based data storage (`.txt`)
- Basic user-specific data handling
- Styled UI using CSS

## 📁 Project Structure

```
JavaFX/
├── src/
│   └── main/
│       └── java/com/ali/javafx/
│           ├── MainApp.java
│           ├── LoginScreen.java
│           └── AddressBook.java
│       └── resources/
│           ├── login.css
│           └── address.css
│           └── address.png
├── users.txt
├── ali.txt (example user data)
├── pom.xml
└── module-info.java
```

## 🚀 How to Run (Using IntelliJ IDEA)

1. Open the project in **IntelliJ IDEA**.
2. Make sure your SDK is set to **Java 11 or above**.
3. Let IntelliJ import the Maven project automatically.
4. Run `MainApp.java` to start the application.

## 🔐 Login Credentials

User data is stored in `users.txt` in the following format:

```
username,password
```

Example:
```
ali,1234
```

Each user has a separate `.txt` file to store their address entries, e.g. `ali.txt`.

## 🛠️ Technologies Used

- Java 11+
- JavaFX
- Maven
- CSS for styling

## 💡 Future Improvements

- Replace text file storage with an embedded database (e.g. SQLite)
- Add user registration and password encryption (e.g. hashing)
- Add features to delete/edit address entries
- Use FXML and Scene Builder for improved UI design

---

### 📌 Author

Developed by Muhammet Ali Taşçı  
Disclaimer : This project was developed with AI.
