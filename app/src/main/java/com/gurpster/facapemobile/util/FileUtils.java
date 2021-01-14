package com.gurpster.facapemobile.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gurpster.facapemobile.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Williaan Lopes (d3x773r) on 18/03/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class FileUtils {

    private static Integer notificationID = R.id.save_pdf;

    public static boolean moveFile(Context context, String inputPath, String outputPath) {

        boolean rt = false;

        InputStream in;
        OutputStream out;

        final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.createNewFile();
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(dir), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                // Configure the notification channel.
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round_)
                    .setTicker("Baixando boleto...")
                    .setContentTitle("Baixando boleto")
                    .setAutoCancel(true)
                    .setProgress(100, 0, false);

            notificationManager.notify(notificationID, builder.build());


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            int fileSizeDownloaded = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);

                if(fileSizeDownloaded < 101) {
                    builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher_round_)
                            .setContentTitle("Baixando boleto")
                            .setAutoCancel(true)
                            .setProgress(100, fileSizeDownloaded++, false);
                    notificationManager.notify(notificationID, builder.build());
                }
            }
            in.close();

            // write the output file
            out.flush();
            out.close();

            // delete the original file
            new File(inputPath).delete();

            rt = true;

            builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round_)
                    .setTicker("Boleto baixado.")
                    .setContentTitle("Donwload completo")
                    .setContentText("facape_boleto.pdf")
                    .setContentIntent(pendingIntent);
            notificationManager.notify(notificationID, builder.build());

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return rt;
    }

    public static boolean moveFile(String inputPath, String inputFile, String outputPath) {

        boolean rt = false;

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdir();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();

            rt = true;
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return rt;
    }

    public static boolean deleteFile(String inputPath, String inputFile) {
        boolean rt = false;
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();
            rt = true;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return rt;
    }

    public static boolean copyFile(String inputPath, String inputFile, String outputPath) {

        boolean rt = false;

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdir();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

            rt = true;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return rt;

    }
}
