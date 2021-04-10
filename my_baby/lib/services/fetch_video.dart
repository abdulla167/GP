import 'dart:convert';
import 'dart:io';

Map<String, Object> splitQueryString(String query) {
  return query.split('&').fold({}, (map, element) {
    final index = element.indexOf("=");
    if (index == -1) {
      if (element != "") {
        map[element] = "";
      }
    } else if (index != 0) {
      var key = element.substring(0, index);
      var value = element.substring(index + 1);
      map[key] = value;
    }
    return map;
  });
}

Future<Map<String, Object>> fetch(String videoId) async {
  final url = "https://www.youtube.com/get_video_info?video_id=${videoId}";
  // mobile
  var request = await HttpClient().getUrl(Uri.parse(url));
  var response = await request.close();
  var content = '';
  await for (var s in response.transform(utf8.decoder)) {
    content += s;
  }
  // web
  // var request = await html.HttpRequest.getString(url);
  // var content = request;

  content = Uri.decodeFull(content);
  var query = splitQueryString(content);
  Map<String, Object> jsonQuery = json.decode(query["player_response"]);
  // jsonQuery.forEach((key, value) {
  //   print(key);
  // });
  // print('---------------');
  Map<String, Object> streamingData = jsonQuery['streamingData'];
  // streamingData.forEach((key, value) {
  //   print(key);
  // });
  // print('---------------');
  List<dynamic> formats = streamingData['formats'];
  Map<String, Object> video = formats[0];
  return video;
  // video.forEach((key, value) {
  //   print(key);
  // });
  // print('---------------');
  // formats.forEach((element) {
  //   print(element['url']);
  //   print(element['quality']);
  // });
}

Future<String> fetchVideoInfo(String videoId) async {
  try {
    var video = await fetch(videoId);
    if (video['url'] != null) {
      return video['url'];
    }
    return null;
  } catch (e) {
    print(e);
    return null;
  }
}
