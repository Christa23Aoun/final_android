HUG YOUR MUG – ANDROID COFFEE ORDERING APP 
Christa-Maria Aoun    Roy Chahoud

PROJECT OVERVIEW

Hug Your Mug is a modern Android coffee ordering application developed using Kotlin, the MVVM architectural pattern, and Firebase.

The application allows users to browse coffee menus, manage a cart, place pickup or delivery orders, earn loyalty points, receive notifications, and track their order history.

The main objectives of this project are:

Applying a clean Android architecture based on MVVM

Using Firebase Authentication and Cloud Firestore

Implementing a realistic e-commerce order workflow

Handling background notifications using WorkManager

Managing navigation using BottomNavigationView and NavController

ARCHITECTURE OVERVIEW (MVVM)

The project follows the MVVM (Model–View–ViewModel) architectural pattern.

Model
Represents application data such as Order, User, CartItem, and related entities.

View
Activities and Fragments responsible only for UI rendering.

ViewModel
Holds UI state and business logic.

Repository
Handles all Firebase and data access operations.

This architecture ensures:

Clear separation of concerns

Lifecycle-safe data handling

Improved testability and maintainability

No business logic inside UI components

FIREBASE INTEGRATION

Firebase is used as the backend service for the application.

Firebase Authentication is used for user login and registration

Cloud Firestore is used to store users, cart items, orders, and favorites

Notifications are implemented using Firebase-compatible services

PROJECT STRUCTURE EXPLANATION

app/src/main/java/com/example/hugyourmug

CORE APPLICATION FILES

HugYourMugApp.kt Application class used for global initialization.

MainActivity.kt Hosts the NavHostFragment and BottomNavigationView. Responsible for navigation setup, menu seeding, and notification scheduling.

MyFirebaseMessagingService.kt Handles Firebase messaging callbacks and notification infrastructure.

DATA MODELS

User.kt Represents user profile data and loyalty points.

Coffee.kt Defines coffee items available in the menu.

CoffeeMenuItem.kt Represents menu display items.

CartItem.kt Defines items stored in the shopping cart.

FavoriteItem.kt Represents favorite coffee items.

Order.kt Stores order metadata such as delivery type, total price, timestamp, and mood.

OrderItem.kt Represents individual items inside an order.

REPOSITORIES

UserRepository.kt Handles authentication and user-related data.

ProfileRepository.kt Manages profile data and loyalty points.

CoffeeRepository.kt Retrieves coffee menu data.

CartRepository.kt Manages cart operations.

FavoriteRepository.kt Handles favorite items.

OrderRepository.kt Handles order creation and order history retrieval.

MenuSeeder.kt Seeds initial menu items and moods into Firestore.

USER INTERFACE LAYER

Authentication
WelcomeActivity.kt
LoginActivity.kt
RegisterActivity.kt

Home
HomeFragment.kt

Menu
MenuFragment.kt
CoffeeMenuAdapter.kt

Cart
CartFragment.kt
CartAdapter.kt

Checkout
CheckoutFragment.kt
CheckoutItemsAdapter.kt

Orders
OrderHistoryFragment.kt
OrderHistoryAdapter.kt
OrderDetailsFragment.kt
OrderDetailsItemsAdapter.kt

Profile
ProfileFragment.kt

Favorites
FavoritesFragment.kt
FavoritesAdapter.kt

Maps
MapsActivity.kt
MapsFragment.kt

Mood Feature
MoodSelectionFragment.kt
MoodResultFragment.kt
MoodAdapter.kt

NOTIFICATIONS

NotificationHelper.kt
Handles notification channel creation and display.

CtaWorker.kt
Schedules promotional notifications.

OrderConfirmationWorker.kt
Displays a delayed notification indicating that the order is being prepared.

NOTIFICATION WORKFLOW

The user places an order

An immediate in-app message confirms that the order was placed successfully

After 30 seconds, a system notification appears indicating that the order is being prepared

This workflow is implemented using WorkManager.

VIEWMODEL LAYER

CartViewModel.kt
CheckoutViewModel.kt
CoffeeViewModel.kt
FavoritesViewModel.kt
OrdersViewModel.kt
ProfileViewModel.kt

All ViewModels:

Use LiveData

Use Kotlin coroutines for asynchronous operations

Contain no UI or Firebase code

TESTING

androidTest
Contains instrumented UI tests.

test
Contains unit test placeholders.

KEY FEATURES SUMMARY

Firebase Authentication

Firestore database integration

MVVM architecture

Bottom navigation

Cart and checkout system

Pickup and delivery support

Loyalty points system

Background notifications

Google Maps integration

CONCLUSION

Hug Your Mug is a complete Android application that demonstrates modern Android development practices.

It follows a clean MVVM architecture, integrates Firebase correctly, and provides a realistic user flow suitable for academic evaluation, technical defense, and future extension.
