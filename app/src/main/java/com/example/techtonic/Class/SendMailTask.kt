package com.example.techtonic.Class

import android.os.AsyncTask
import java.util.Properties
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMailTask(
    private val recipientEmail: String,
    private val subject: String,
    private val messageBody: String
) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            // Set properties for Gmail SMTP
            val properties = Properties()
            properties["mail.smtp.host"] = "smtp.gmail.com"
            properties["mail.smtp.socketFactory.port"] = "465"
            properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.port"] = "465"

            // Set up Gmail authentication
            val session = Session.getDefaultInstance(properties, object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    // Use your Gmail address and app password (not the regular account password)
                    return PasswordAuthentication("your-email@gmail.com", "your-app-password")
                }
            })

            // Create email message
            val message = MimeMessage(session)
            message.setFrom(InternetAddress("your-email@gmail.com"))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail))
            message.subject = subject
            message.setText(messageBody)

            // Send email
            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
