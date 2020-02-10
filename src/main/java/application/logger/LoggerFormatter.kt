
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.*
import java.util.logging.Formatter


class LoggerFormatter : Formatter() {

    override fun format(record: LogRecord): String {
        val buf = StringBuffer(100)
        buf.append("<tr>\n")

        if (record.level.intValue() >= Level.WARNING.intValue()) {
            buf.append("\t<td style=\"color:red\">")
            buf.append("<b>")
            buf.append(record.level)
            buf.append("</b>")
        } else {
            buf.append("\t<td>")
            buf.append(record.level)
        }

        buf.append("</td>\n")
        buf.append("\t<td>")
        buf.append(calcDate(record.millis))
        buf.append("</td>\n")
        buf.append("\t<td>")
        buf.append(formatMessage(record))
        buf.append("</td>\n")
        buf.append("</tr>\n")

        return buf.toString()
    }

    private fun calcDate(milliseconds: Long) : String {
        val dateFormat = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
        val resultDate = Date(milliseconds)
        return dateFormat.format(resultDate)
    }

    override fun getHead(h: Handler): String {
        return ("<!DOCTYPE html>\n<head>\n<style>\n"
                + "table { width: 100% }\n"
                + "th { font:bold 10pt Tahoma; }\n"
                + "td { font:normal 10pt Tahoma; }\n"
                + "h1 {font:normal 11pt Tahoma;}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>" + Date() + "</h1>\n"
                + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
                + "<tr align=\"left\">\n"
                + "\t<th style=\"width:10%\">Loglevel</th>\n"
                + "\t<th style=\"width:15%\">Time</th>\n"
                + "\t<th style=\"width:75%\">Log Message</th>\n"
                + "</tr>\n")
    }

    override fun getTail(h: Handler): String {
        return "</table>\n</body>\n</html>"
    }


}