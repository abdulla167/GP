import 'package:flutter/material.dart';
import 'tab_navigation/tab_item.dart';

class NavigatorKeys {
  static final rootNavigationKey = GlobalKey<NavigatorState>();
  static final tabNavigatorKeys = {
    TabItem.TIMELINE: GlobalKey<NavigatorState>(),
    TabItem.HEALTH: GlobalKey<NavigatorState>(),
    TabItem.RESOURCES: GlobalKey<NavigatorState>(),
    TabItem.PROFILE: GlobalKey<NavigatorState>(),
  };
}
