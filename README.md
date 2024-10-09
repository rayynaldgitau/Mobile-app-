Jobify 🚀
Welcome to **Jobify**, your go-to job portal app! This Android application is built using **Kotlin**, and it leverages **Firebase Storage** and **Firebase Realtime Database** to provide a seamless experience for job seekers and employers alike.
Features 🌟
- User Authentication: Sign up and sign in using Firebase Authentication.
- Job Posting: Companies can post job listings.
- Job Applications: Job seekers can apply for posted jobs.
- Application Management: Employers can accept or reject applicants.
- Email Notifications: Send email notifications to users.
- Job Categories: Jobs are grouped into categories (still in development).
- Beautiful Icons: The app includes wonderful icons for an enhanced user experience.
Getting Started 🛠️
Prerequisites
Make sure you have the following installed on your machine:
- Android Studio
- Kotlin
- Firebase account
Downloading the Project
Clone the repository to your local machine:
```bash
https://github.com/rayynaldgitau/Mobile-app-.git
```
Navigate to the project directory:
```bash
cd jobify
```
Configuring the Project 🔧
1. Open the Project in Android Studio:
- Launch Android Studio and open the `Jobify` project.
2. Set Up Firebase:
Go to the [Firebase Console](https://console.firebase.google.com/).
Create a new project and add your Android app.
Download the `google-services.json` file and place it in the `app/` directory of your project.
3. Add Dependencies:
Open the `build.gradle` (Module: app) file and add the necessary Firebase dependencies:
```groovy
implementation 'com.google.firebase:firebase-auth:21.0.1'
implementation 'com.google.firebase:firebase-database:20.2.1'
implementation 'com.google.firebase:firebase-storage:20.0.0'
```
Sync the project.
4. Configure Firebase Authentication:
- Enable Email/Password authentication in the Firebase console under Authentication.
5. Run the App:
- Connect your Android device or use an emulator.
- Click on the **Run** button in Android Studio to launch the app.
Screenshots 📸
![launch Screen](https://github.com/user-attachments/assets/76bfc123-2228-4632-b342-489cd73796b0)
![main Page ](https://github.com/user-attachments/assets/fe42c0c1-91e1-4632-a021-8c9b1ebed23c)
![Screenshot 2024-10-09 055315](https://github.com/user-attachments/assets/fc3d50ed-50c2-4153-8cf0-655535ada168)
![Screenshot 2024-10-09 055344](https://github.com/user-attachments/assets/ba865c4f-9faf-4a20-b207-5ce36daa44b4)
![Screenshot 2024-10-09 055435](https://github.com/user-attachments/assets/1caefe59-4f2f-4b40-b129-e319b24f4c89)
![Screenshot 2024-10-09 055507](https://github.com/user-attachments/assets/9fb40870-6f83-4d9d-a5d2-9e35669af007)
![Sign In](https://github.com/user-attachments/assets/e79b28fa-4a80-41a6-a05e-0f5a9712884b)
![Sign Up screen](https://github.com/user-attachments/assets/c260bdb7-9600-48fd-972e-88aeb920bae4)

Contributing 🤝
Feel free to fork the repository and submit pull requests. We welcome any contributions or suggestions to improve Jobify!
License 📄
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
Acknowledgements 🙏
- Thanks to the Firebase team for providing a robust backend solution.
- Special thanks to the designers for the wonderful icons used in this app.

---


