package application.usb;

import javax.usb.*;
import java.util.List;
import javax.usb.UsbEndpointDescriptor;

import static java.lang.Thread.sleep;

public class UsbControllerImpl implements UsbController {

    private static UsbControllerImpl instance;
    private UsbEndpoint endpoint;
    private UsbInterface iFace;

    public static UsbControllerImpl newInstance() throws UsbException {
        if(instance == null){
            instance = new UsbControllerImpl();
        }
        return instance;
    }

    private UsbControllerImpl() throws UsbException {

        UsbServices services;

        try {
            services = UsbHostManager.getUsbServices();
            UsbDevice usbDevice = getUsbDevice(services.getRootUsbHub(), (short)0x1a86, (short)0x7523);
            UsbConfiguration configuration = usbDevice.getActiveUsbConfiguration();
            iFace = configuration.getUsbInterface((byte) 0);
            iFace.claim(new UsbInterfacePolicy() {
                public boolean forceClaim(UsbInterface usbInterface) {
                    return true;
                }
            });


        } catch (UsbException e) {
            if(iFace != null && iFace.isClaimed()){
                iFace.release();
            }
            e.printStackTrace();
        }

    }

    private void configureSend() {
        //endpoint = iFace.getUsbEndpoint((byte) 0x04);

        List<UsbEndpoint> endpoints = iFace.getUsbEndpoints();
        UsbEndpointDescriptor ed1 =
                endpoints.get(0).getUsbEndpointDescriptor();
        UsbEndpointDescriptor ed2 =
                endpoints.get(1).getUsbEndpointDescriptor();

        byte a1 = ed1.bEndpointAddress();
        byte a2 = ed2.bEndpointAddress();

        byte in = 0 , out = 0;

        if (((a1 & UsbConst.ENDPOINT_DIRECTION_IN) != 0) &&
                ((a2 & UsbConst.ENDPOINT_DIRECTION_IN) == 0)) {
            in = a1;
            out = a2;
        } else if (((a2 & UsbConst.ENDPOINT_DIRECTION_IN) != 0) &&
                ((a1 & UsbConst.ENDPOINT_DIRECTION_IN) == 0)) {
            out = a1;
            in = a2;
        }

        //System.out.println(String.valueOf(in));
        //System.out.println(String.valueOf(out));

        //endpoint = iFace.getUsbEndpoint((byte) 0x04);
        endpoint = iFace.getUsbEndpoint(out);

    }

    private void configureReceive() {
        //endpoint = iFace.getUsbEndpoint((byte) 0x83);

        List<UsbEndpoint> endpoints = iFace.getUsbEndpoints();
        UsbEndpointDescriptor ed1 =
                endpoints.get(0).getUsbEndpointDescriptor();
        UsbEndpointDescriptor ed2 =
                endpoints.get(1).getUsbEndpointDescriptor();

        byte a1 = ed1.bEndpointAddress();
        byte a2 = ed2.bEndpointAddress();

        byte in = 0 , out = 0;

        if (((a1 & UsbConst.ENDPOINT_DIRECTION_IN) != 0) &&
                ((a2 & UsbConst.ENDPOINT_DIRECTION_IN) == 0)) {
            in = a1;
            out = a2;
        } else if (((a2 & UsbConst.ENDPOINT_DIRECTION_IN) != 0) &&
                ((a1 & UsbConst.ENDPOINT_DIRECTION_IN) == 0)) {
            out = a1;
            in = a2;
        }

        //System.out.println(String.valueOf(in));
        //System.out.println(String.valueOf(out));

        //endpoint = iFace.getUsbEndpoint((byte) 0x04);
        endpoint = iFace.getUsbEndpoint(in);
    }

    private  UsbDevice getUsbDevice(UsbHub host, short vendorId, short productId) {
        for (UsbDevice device : (List<UsbDevice>) host.getAttachedUsbDevices()) {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if (desc.idVendor() == vendorId && desc.idProduct() == productId) {
                return device;
            }

            if (device.isUsbHub()) {
                device = getUsbDevice((UsbHub) device, vendorId, productId);
                if (device != null) return device;
            }
        }
        return null;
    }

    public  void sendCommand(String command) throws UsbException {
        configureSend();
        UsbPipe pipe = null;
        try {
            pipe = endpoint.getUsbPipe();
            pipe.open();
            pipe.syncSubmit(command.getBytes());
            if(pipe.isOpen()) {
                pipe.close();
            }

        } catch (UsbException e) {
            e.printStackTrace();
        } finally {
            if(pipe != null && pipe.isOpen()){
                pipe.close();
            }
        }
    }

    private String cleanString(byte[] byteArray) {
        String stringResult = "";
        for(byte b : byteArray) {
            if(b != 0) {
                stringResult += Character.toString((char) b);
            }
        }
        return stringResult;
    }


    public String readCommand() throws UsbException, InterruptedException {
        return readCommand("");
    }

    private String readCommand(String response) throws UsbException, InterruptedException {
        configureReceive();
        UsbPipe pipe = null;
        byte[] aux = new byte[1];

        try {
            pipe = endpoint.getUsbPipe();
            pipe.open();
            pipe.syncSubmit(aux);

            if(aux[0] == 0) {
                sleep(1000);
                pipe.close();
                return readCommand(response);
            } else if(aux[0] != '\n') {
                response += cleanString(aux);
                pipe.close();
                return readCommand(response);
            }


            if(pipe.isOpen()) {
                pipe.close();
            }

        } catch (UsbException e) {
            //getLogger().info("UsbController", e.toString());
        } finally {
            if(pipe != null && pipe.isOpen()){
                pipe.close();
            }
        }

        return response;
    }

    @Override
    public void eraseResource() {
        try {
            iFace.release();
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }
}
