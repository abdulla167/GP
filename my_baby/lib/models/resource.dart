class Section {
  final String title;
  final List<Subsection> subsections;

  Section(this.title, this.subsections);
}

class Subsection {
  final String title;
  final List<Object> content;

  Subsection(this.title, this.content);
}



class HtmlText {
  final String text;

  HtmlText(this.text);
}

class ImageContent {
  final String imgUrl;
  
  ImageContent(this.imgUrl);
}

class BulletListContent {
  final Object title;
  final List<Object> items;

  BulletListContent(this.title, this.items);
}

class VideoContent {
  final String videoId;

  VideoContent(this.videoId);
}
