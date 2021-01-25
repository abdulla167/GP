import 'package:flutter/material.dart';


import 'resource_content_screen.dart';
import '../../widgets/resource_subsection.dart';
import '../../models/resource.dart';

class ResourceSubsectionsScreen extends StatelessWidget {
  static const route = '/resourceSubsections';

  @override
  Widget build(BuildContext context) {
    final Map<String, Object> args = ModalRoute.of(context).settings.arguments;
    final title = args['title'];
    final List<Subsection> subsections = args['subsections'];
    final theme = Theme.of(context);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: theme.scaffoldBackgroundColor,
        elevation: 0,
        title: Text(
          title,
          style:
              TextStyle(fontWeight: FontWeight.w500, color: Color(0xff364861)),
        ),
        centerTitle: true,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back_ios,
            color: theme.primaryColor,
            size: 20,
          ),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ),
      body: ListView.builder(
        itemBuilder: (ctx, index) {
          return ResourceSubSection(
            title: subsections[index].title,
            onTap: () {
              Navigator.of(context).pushNamed(ResourceContentScreen.route, arguments:{
                'title': subsections[index].title,
                'content': subsections[index].content
              });
            },
          );
        },
        itemCount: subsections.length,
        padding: EdgeInsets.only(top: 5),
      ),
    );
  }
}
