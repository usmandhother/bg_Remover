
# ✂️ Huawei ML Kit Background Removal - Android Kotlin Sample  

This repository showcases a basic Android application written in **Kotlin** that leverages **Huawei ML Kit** to perform background removal on images. It demonstrates the use of **image segmentation APIs** with predefined models such as body, multi-class, and hair segmentation. 🚀  

## ✨ Features  
- 📷 **Image Selection**: Users can select an image from their gallery.  
- ✂️ **Background Removal**: Uses Huawei ML Kit's segmentation models to remove the background effectively.  
- 🛠️ **Customizable & Lightweight**: Focused on simplicity and flexibility for developers.  

## 🛠️ Requirements  
- **Huawei Mobile Services (HMS)**: Ensure HMS is integrated into your device or emulator.  
- **Huawei Developer Console**: Configure the project and download the `agconnect-services.json` file.  
- **Android Studio Arctic Fox or later**: Support for Kotlin and modern Android libraries.  

## 🚀 Setup Instructions  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/yourusername/bg-removal-huawei-mlkit.git  
   ```  
2. Open the project in Android Studio.  
3. Ensure the `agconnect-services.json` file is added to the `app` directory.  
4. Sync the following dependencies from the `build.gradle` file:  
   ```gradle  
   // Huawei Image Segmentation  
   implementation("com.huawei.hms:ml-computer-vision-segmentation:3.7.0.302")  
   implementation("com.huawei.hms:ml-computer-vision-image-segmentation-multiclass-model:3.7.0.302")  
   implementation("com.huawei.hms:ml-computer-vision-image-segmentation-body-model:3.7.0.302")  
   implementation("com.huawei.hms:ml-computer-vision-image-segmentation-hair-model:3.7.0.302")  
   ```  
5. Build and run the app on a device or emulator with **HMS Core** installed.  

## 🎮 How It Works  
1. **Select an Image**: Pick an image from your device.  
2. **Process the Image**: The app removes the background using segmentation models.  
3. **View the Result**: The processed image is displayed with the background removed.  

## 📜 License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.  
