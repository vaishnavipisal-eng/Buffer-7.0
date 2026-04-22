# ✈️ Aircraft Maintenance System

A Java-based application to manage aircraft maintenance efficiently.
This system allows users to track aircraft details, monitor parts, and identify urgent repairs using both **CLI (console)** and **GUI (Swing)** interfaces.

---

## 📌 Features

* Add and manage aircraft details
* Add parts with maintenance status
* Automatic **repair priority detection** (Critical / Moderate / Low)
* Search aircraft by ID, model, or airline
* View urgent repairs instantly
* Delete aircraft records
* Persistent data storage using file serialization
* Dual interface:

  * Console-based menu
  * GUI using Java Swing

---

## 🛠️ Technologies Used

* Java
* Object-Oriented Programming (OOP)
* Java Swing (GUI)
* File Handling (Serialization)

---

## 📂 Project Structure

```
aircraft_management/
│
├── Aircraft.java        # Aircraft & Part classes with logic
├── FileHelper.java      # Handles saving & loading data
├── MainMenu.java        # Console-based interface
├── MainUI.java          # GUI-based interface
└── fleet.dat            # Stored data file (auto-generated)
```

---

## ⚙️ How It Works

### 1. Aircraft Data

Each aircraft stores:

* ID, model, airline
* Flying hours
* Fuel level
* Engine temperature
* Total landings
* Maintenance history

### 2. Smart Maintenance Detection

The system automatically assigns part status:

* 🔴 **Critical** → Immediate repair needed
* 🟠 **Moderate** → Needs attention
* 🟢 **Low** → Safe

Based on conditions like:

* Low fuel
* High engine temperature
* Excess flying hours
* Maintenance delay

---

## 🖥️ How to Run

### ▶️ Run Console Version

```
javac aircraft_management/*.java
java aircraft_management.MainMenu
```

---

### 🖼️ Run GUI Version

```
java aircraft_management.MainUI
```

---

## 📸 Functional Overview

Main operations available in the system:

* Add Aircraft
* Add Part
* Search Aircraft
* Show Urgent Repairs
* Delete Aircraft
* Save & Exit

---

## 🚀 Future Improvements

* Database integration (MySQL)
* User authentication system
* Web-based version
* Real-time alerts & notifications
* Graphical analytics dashboard

---

## 📖 Learning Outcomes

This project demonstrates:

* Object-Oriented Design
* File Handling in Java
* GUI Development using Swing
* Real-world problem solving

---

## 👩‍💻 Author

**Vaishnavi Pisal**

---

## ⭐ Contribute

Feel free to fork this repository and improve the system!

## Video Link - 
https://drive.google.com/file/d/1lU4AvgauM4PPLzqR85IVE-cSkNhATrtm/view?usp=sharing
