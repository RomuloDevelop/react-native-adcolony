
# react-native-adcolony

## Getting started

`$ npm install react-native-adcolony --save`

### Mostly automatic installation

`$ react-native link react-native-adcolony`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-adcolony` and add `RNAdcolony.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAdcolony.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAdcolonyPackage;` to the imports at the top of the file
  - Add `new RNAdcolonyPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-adcolony'
  	project(':react-native-adcolony').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-adcolony/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-adcolony')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAdcolony.sln` in `node_modules/react-native-adcolony/windows/RNAdcolony.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Adcolony.RNAdcolony;` to the usings at the top of the file
  - Add `new RNAdcolonyPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAdcolony from 'react-native-adcolony';

// TODO: What to do with the module?
RNAdcolony;
```
  