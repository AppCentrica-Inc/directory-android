var jsonText = '{ "contacts": [';

// Ed
var name = '"name": "Ed Nemes"';
var email = '"email": "ed.nemes@appcentrica.com"';
var title = '"title": "President and CEO"';
var number = '"number": "' + "123-456-7890" + '"';
var linkedIn = '"linkedIn": "http://ca.linkedin.com/pub/ed-nemes/2/6a7/107"';
var pictureUrl = '"pictureUrl": "' + "http://appcentrica.com/media/1192/people_ed.jpg?width=200&height=180" + '"';
jsonText += "{" + name + ", " + email + ", " + title + ", " + number + ", " + linkedIn + ", " + pictureUrl + "},";

// Ken
var name = '"name": "Ken Kitamura"';
var email = '"email": "ken.kitamura@appcentrica.com"';
var title = '"title": "Chief Architect"';
var number = '"number": "' + "123-456-7890" + '"';
var linkedIn = '"linkedIn": "http://ca.linkedin.com/in/kitamura"';
var pictureUrl = '"pictureUrl": "' + "http://appcentrica.com/media/1193/people_ken.jpg?width=200&height=180" + '"';
jsonText += "{" + name + ", " + email + ", " + title + ", " + number + ", " + linkedIn + ", " + pictureUrl + "},";

// Rob
var name = '"name": "Rob Lokinger"';
var email = '"email": "rob.lokinger@appcentrica.com"';
var title = '"title": "Director of Delivery"';
var number = '"number": "' + "123-456-7890" + '"';
var linkedIn = '"linkedIn": "http://ca.linkedin.com/pub/rob-lokinger/0/253/5b9"';
var pictureUrl = '"pictureUrl": "' + "http://appcentrica.com/media/1194/people_rob.jpg?width=200&height=180" + '"';
jsonText += "{" + name + ", " + email + ", " + title + ", " + number + ", " + linkedIn + ", " + pictureUrl + "},";

$('.employee-cage').each(function( index ) {
  var name = '"name": "' + $(this).find(".employee-name h5").html() + '"';
  var email = '"email": "' + $(this).find(".employee-links a")[1].href.replace("mailto:", "") + '"';
  var title = '"title": "' + $(this).find(".employee-title").html() + '"';
  var number = '"number": "' + "123-456-7890" + '"';
  var linkedIn = '"linkedIn": "' + $(this).find(".employee-links a")[0].href.replace("mailto:", "") + '"';
  var pictureUrl = '"pictureUrl": "' + "http://appcentrica.com" + $(this).find(".face img").attr("src") + '"';
  jsonText += "{" + name + ", " + email + ", " + title + ", " + number + ", " + linkedIn + ", " + pictureUrl + "},";
});
jsonText = jsonText.slice(0,-1); // removes last comma
jsonText += "]}";
console.log(jsonText);