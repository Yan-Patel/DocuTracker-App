# Docu Tracker - Customer Document Record System (Android + Firebase)

## Setup (one time)
1. Android Studio me ye folder open karo (File > Open).
2. https://console.firebase.google.com par naya project banao.
3. Project me Android app add karo, package name: `com.example.customerdocs`
4. `google-services.json` download karke `app/` folder me paste karo.
5. Firebase Console > Build > Authentication > Sign-in method > **Email/Password** enable karo.
6. Build > Firestore Database > Create database > **Test mode** me start karo.
7. Run karo. Sign up -> Form -> View Customers -> Add Dummy Data.

## Flow
Login/Signup -> Customer Form (Name, Phone, DOB, Email, Address + Document) -> Firestore `customers` collection -> List screen.

## Report format (outline)
1. Title page  2. Abstract  3. Introduction  4. Objectives  5. Tools & Technologies
6. System design (flowchart / ER / DFD)  7. Firebase structure  8. Screenshots (Login, Form, List, Firebase console)
9. Code snippets  10. Conclusion  11. References
