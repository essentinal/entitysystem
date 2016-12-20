package es.core.world;

import java.util.concurrent.ConcurrentHashMap;

import es.core.system.ESystem;

public class EWorld extends Thread {
  private static final long MILLI_TO_NANO = 1000000L;
  private static final long TO_NANO = 1000000000L;

  private final ConcurrentHashMap<Class<? extends ESystem>, ESystem> systems = new ConcurrentHashMap<Class<? extends ESystem>, ESystem>();
  private long lastExecTime;
  private long lastSleepTime;

  private long targetTpf;
  private final boolean isEnabled = true;

  public EWorld() {
    setDaemon(true);
  }

  public void update(final float tpf) {
    for (final ESystem eSystem : systems.values()) {
      eSystem.process(tpf);
    }
  }

  @Override
  public void run() {
    while (isEnabled) {
      final long start = System.nanoTime();

      update((float) ((lastExecTime + lastSleepTime) / (double) TO_NANO));

      lastExecTime = System.nanoTime() - start;

      lastSleepTime = targetTpf - lastExecTime;

      if (lastSleepTime > 0L) {
        try {
          sleep(lastSleepTime / MILLI_TO_NANO);
        } catch (final InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void setUpdatesPerSecond(final long ups) {
    targetTpf = TO_NANO / ups;
  }

  public void addSystem(final ESystem system) {
    systems.put(system.getClass(), system);
  }

  public void removeSystem(final Class<? extends ESystem> clss) {
    systems.remove(clss);
  }

  public boolean hasSystem(final Class<? extends ESystem> clss) {
    return systems.contains(clss);
  }
}
