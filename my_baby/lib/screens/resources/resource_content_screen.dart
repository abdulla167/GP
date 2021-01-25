import 'package:flutter/material.dart';


import '../../models/resource.dart';
import '../../widgets/bullet.dart';

class ResourceContentScreen extends StatelessWidget {
  static const route = '/resourceContent';

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final Map<String, Object> args = ModalRoute.of(context).settings.arguments;
    final title = args['title'];
    final List<Content> contents = args['content'];
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
        itemCount: contents.length,
        itemBuilder: (ctx, index) {
          final content = contents[index];
          if (content is TextContent) {
            return Container(
              margin: EdgeInsets.symmetric(vertical: 5),
              child: Text(content.text, textAlign: TextAlign.justify),
            );
          } else if (content is ImageContent) {
            return Container(
              child: Image.asset(content.imgUrl, fit: BoxFit.scaleDown),
            );
          } else if (content is BulletListContent) {
            return Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                if (content.title != null) Text(content.title),
                for (var item in content.items) Bullet(item),
              ],
            );
          } else {
            return Container();
          }
        },
        padding: EdgeInsets.symmetric(horizontal: 8),
      ),
    );
  }
}
