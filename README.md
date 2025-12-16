🛒 Online shop app
A modern Android marketplace application that allows users to browse products, filter by categories, save favorites, and manage their profile.
📱 Features
Core Functionality

Login: Email/password login form with input validation and secure storage access and refresh tokens upon successful login
Product Browsing: View all available products with detailed information
Category Filtering: Filter products by categories using an intuitive ChipGroup interface
Wishlist: Save and manage favorite products locally
User Profile: View and manage user information

Technical Highlights

Single Selection Filtering: Smooth category filtering with Material Design chips
Offline Wishlist: Products are saved locally using Room Database
Automatic Token Management: Seamless authentication with automatic token refresh
Modern UI: RecyclerView with efficient data binding and smooth scrolling

🏗️ Architecture & Tech Stack
Architecture Pattern

MVVM (Model-View-ViewModel): Clean separation of concerns
Repository Pattern: Single source of truth for data operations
LiveData/StateFlow: Reactive UI updates

Libraries & Technologies
Networking

Retrofit: REST API communication
OkHttp Interceptor: Request/response logging and modification
OkHttp Authenticator: Automatic token refresh on 401 errors
Gson Converter: JSON serialization/deserialization

Local Storage

Room Database: Local persistence for wishlist
SharedPreferences: Token and user session management

UI Components

Material Design 3: Modern UI components
RecyclerView: Efficient list rendering
ChipGroup: Category filtering interface
ViewBinding: Type-safe view references
Navigation Component: Fragment navigation

https://github.com/user-attachments/assets/ef91ef6c-e2f2-4f2f-b2f4-28403fa71aae

