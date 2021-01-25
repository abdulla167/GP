import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'tab_navigation/tab_navigator.dart';
import 'tab_navigation/bottom_navigation.dart';
import 'tab_navigation/tab_item.dart';

class TabsScreen extends StatefulWidget {
  @override
  _TabsScreenState createState() => _TabsScreenState();
}

class _TabsScreenState extends State<TabsScreen> {
  final _navigatorKeys = {
    TabItem.TIMELINE: GlobalKey<NavigatorState>(),
    TabItem.HEALTH: GlobalKey<NavigatorState>(),
    TabItem.RESOURCES: GlobalKey<NavigatorState>(),
    TabItem.PROFILE: GlobalKey<NavigatorState>(),
  };

  List<Widget> navigators;
  Map<TabItem, int> navigatorIndex;

  @override
  void initState() {
    super.initState();
    navigators = [_buildTabNavigator(_currentTab)];
    navigatorIndex = {_currentTab: 0};
  }

  TabItem _currentTab = TabItem.RESOURCES;
  TabItem _previousTab;

  void _onSelectTab(TabItem tabItem) {
    if (tabItem == _currentTab) {
      // pop to first route
      _navigatorKeys[tabItem].currentState.popUntil((route) => route.isFirst);
    } else {
      if (!navigatorIndex.containsKey(tabItem)) {
        navigators.add(_buildTabNavigator(tabItem));
        navigatorIndex[tabItem] = navigators.length - 1;
      }
      _previousTab = _currentTab;
      setState(() => _currentTab = tabItem);
    }
  }

  _selectPreviousTab(TabItem tabItem) {
    setState(() => _currentTab = tabItem);
    _previousTab = null;
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      // handle the android back bottom
      onWillPop: () async {
        final isFirstRouteInCurrentTab =
            !await _navigatorKeys[_currentTab].currentState.maybePop();
        if (isFirstRouteInCurrentTab && _previousTab != null) {
          _selectPreviousTab(_previousTab);
          return false;
        } else if (isFirstRouteInCurrentTab && _currentTab != TabItem.TIMELINE) {
            _selectPreviousTab(TabItem.TIMELINE);
            return false;
        }
        // let system handle back button if we're on the first route
        return isFirstRouteInCurrentTab;
      },
      child: Scaffold(
        body: IndexedStack(
          index: navigatorIndex[_currentTab],
          children: navigators,
        ),
        bottomNavigationBar: BottomNavigation(
          currentTab: _currentTab,
          onSelectTab: _onSelectTab,
        ),
      ),
    );
  }

  Widget _buildTabNavigator(TabItem tabItem) {
    return TabNavigator(
      navigatorKey: _navigatorKeys[tabItem],
      initialRoute: tabs[tabItem].initialRoute,
      routes: tabs[tabItem].routes,
    );
  }
}
