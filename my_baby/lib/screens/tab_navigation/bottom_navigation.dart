import 'package:flutter/material.dart';

import 'tab_item.dart';

class BottomNavigation extends StatelessWidget {

  BottomNavigation({@required this.currentTab, @required this.onSelectTab});

  final TabItem currentTab;
  final ValueChanged<TabItem> onSelectTab;
  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: currentTab.index,
      items: [
        _buildItem(TabItem.TIMELINE),
        _buildItem(TabItem.HEALTH),
        _buildItem(TabItem.RESOURCES),
        _buildItem(TabItem.PROFILE)
      ],
      onTap: (index){
        onSelectTab(TabItem.values[index]);
      },
      type: BottomNavigationBarType.fixed,
      backgroundColor: Colors.white,
      unselectedItemColor:
      Theme.of(context).bottomNavigationBarTheme.unselectedItemColor,
      selectedItemColor:
      Theme.of(context).bottomNavigationBarTheme.selectedItemColor,
    );
  }

  BottomNavigationBarItem _buildItem(TabItem tabItem) {
    return BottomNavigationBarItem(
      icon: tabs[tabItem].icon,
      label: tabs[tabItem].name,
    );
  }
}
