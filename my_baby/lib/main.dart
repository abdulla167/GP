import 'package:flutter/material.dart';
import 'package:my_baby/screens/navigator_keys.dart';
import './screens/tabs_screen.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'My baby',
      theme: ThemeData(
        primaryColor: Color(0xff2d74a3),
        canvasColor: Colors.white,
        fontFamily: 'Raleway',
        textTheme: ThemeData.light().textTheme.copyWith(
              headline1: TextStyle(
                color: Colors.white,
                fontSize: 18,
                fontWeight: FontWeight.w500,
              ),
            ),
        bottomNavigationBarTheme: BottomNavigationBarThemeData(
          backgroundColor: Colors.white,
          unselectedItemColor: Color(0xff193a9b),
          selectedItemColor: Theme.of(context).primaryColor,
        ),
      ),
      navigatorKey: NavigatorKeys.rootNavigationKey,
      initialRoute: '/',
      routes: {
        '/': (ctx) => TabsScreen(),
        '/test': (ctx) => Container()
      },
      onUnknownRoute: (settings) {
        return MaterialPageRoute(
          builder: (ctx) => TabsScreen(),
        );
      },
    );
  }
}
