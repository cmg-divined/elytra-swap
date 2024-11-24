package org.example;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 * Example rusherhack plugin
 *
 * @author
 */
public class ElytraSwap extends Plugin {

    @Override
    public void onLoad() {

        // logger
        this.getLogger().info("Hello World!");

        // **Add the following code to register the ElytraSwapModule**

        // creating and registering the ElytraSwapModule
        final ElytraSwapModule elytraSwapModule = new ElytraSwapModule();
        RusherHackAPI.getModuleManager().registerFeature(elytraSwapModule);

    }

    @Override
    public void onUnload() {
        this.getLogger().info("ElytraSwap unloaded!");
    }

}