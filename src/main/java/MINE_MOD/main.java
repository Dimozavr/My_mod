package MINE_MOD;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(main.MODID)
public class main {
    public final static String MODID = "minemod";
    public static IEventBus modEventBus;
    public main() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(StartupCommon.class);
    }
}