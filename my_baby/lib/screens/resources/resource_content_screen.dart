import 'package:flutter/material.dart';
import 'package:my_baby/widgets/html_widget.dart';
import 'package:my_baby/widgets/my_appbar.dart';
import 'package:my_baby/widgets/video_widget.dart';
import '../../models/resource.dart';
import '../../widgets/bullet.dart';

class ResourceContentScreen extends StatelessWidget {
  static const route = '/resourceContent';

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final Map<String, Object> args = ModalRoute.of(context).settings.arguments;
    final title = args['title'];
    final List contents = args['content'];
    return Scaffold(
      appBar: buildAppBar(context, title),
      body: ListView.builder(
        cacheExtent: double.infinity,
        itemCount: contents.length,
        itemBuilder: (ctx, index) {
          final content = contents[index];
          if (content is String) {
            return Container(
              margin: EdgeInsets.symmetric(vertical: 5),
              child: Text(content, textAlign: TextAlign.justify),
            );
          } else if (content is HtmlText) {
            return Container(
              margin: EdgeInsets.symmetric(vertical: 5),
              child: HtmlWidget(content.text),
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
                if (content.title != null)
                  Padding(
                      padding: const EdgeInsets.only(bottom: 5),
                      child: content.title is String
                          ? Text(content.title)
                          : HtmlWidget((content.title as HtmlText).text)),
                for (var item in content.items)
                  Padding(
                    padding: const EdgeInsets.only(left: 8, bottom: 5),
                    child: Bullet(text: item),
                  ),
              ],
            );
          } else if (content is VideoContent) {
            return VideoWidget(content.videoId,key: ValueKey(content.videoId),);
          } else {
            return Container();
          }
        },
        padding: EdgeInsets.symmetric(horizontal: 8),
      ),
    );
  }
}
