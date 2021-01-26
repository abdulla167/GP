class Section {
  final String title;
  final List<Subsection> subsections;

  Section(this.title, this.subsections);
}

class Subsection {
  final String title;
  final List<Content> content;

  Subsection(this.title, this.content);
}

abstract class Content {}

class TextContent implements Content {
  final String text;

  TextContent(this.text);
}

class ImageContent implements Content {
  final String imgUrl;
  
  ImageContent(this.imgUrl);
}

class BulletListContent implements Content {
  final String title;
  final List<String> items;

  BulletListContent(this.title, this.items);
}
