import threading, queue, time
import logging
logging.basicConfig(level=logging.DEBUG)

C = threading.Condition()
C2 = threading.Condition()
slots = queue.Queue()


def scheduleSlot():
	global slots
	with C:
		while slots.empty():
			logging.debug('wait')
			C.wait()
			while not slots.empty():
				try:
					slot = slots.get_nowait()
					with C2:
						logging.debug("Got %s", slot);
				except:
					print("Unexpected error:", sys.exc_info()[0])



def AddSlot(slot):
	with C:
		logging.debug('new slot %s', slot)
		slots.put(slot)
		C.notify()

t_schedule = threading.Thread( target = scheduleSlot, args = ())
t_schedule.start()

for slot in [1,2,3,4,5]:
	AddSlot(slot)

time.sleep(1)

for slot in [6,7,8]:
	AddSlot(slot)

time.sleep(1)

for slot in [9,10,11]:
	AddSlot(slot)


