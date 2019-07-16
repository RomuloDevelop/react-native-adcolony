
# react-native-adcolony

## Getting started

`$ npm install react-native-adcolony --save`

### Mostly automatic installation

`$ react-native link react-native-adcolony`

### Manual installation

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

## Usage
```javascript
import RNAdcolony from 'react-native-adcolony';

// TODO: What to do with the module?
RNAdcolony;
```
  
