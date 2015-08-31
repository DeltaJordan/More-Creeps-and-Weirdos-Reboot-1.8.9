package creeps.api;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CreepEventHandler{
	
	Random rand = new Random(); 
	 int value = rand.nextInt(35) + 1;
	 
	World World;
	EntityPlayer player;
	 
		@SubscribeEvent
	 	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
			
		 if (value == 1){
		 event.player.addChatComponentMessage(new ChatComponentText("Now, go out there and have some fun!"));
		
		 }
		 else{
			 if (value == 2){
				 event.player.addChatComponentMessage(new ChatComponentText("Don't let those stinky Floobs push you around!"));
				
			 }
			 else{
				 if (value == 3){
					 event.player.addChatComponentMessage(new ChatComponentText("Give a diamond to a level 25 HotDog for a special reward!"));
					
				 }
			 
			 else{
				 if (value == 4){
					 event.player.addChatComponentMessage(new ChatComponentText("Urinating Bums can help with landscaping. Try one today!"));
					
				 }
				 else{
					 if (value == 5){
						 event.player.addChatComponentMessage(new ChatComponentText("You're doing something right!"));
						
					 }
					 else{
						 if (value == 6){
							 event.player.addChatComponentMessage(new ChatComponentText("Watch out for grumpy G's!"));
							
						 }
						 else{
							 if (value == 7){
								 event.player.addChatComponentMessage(new ChatComponentText("Guinea Pigs make nice pets."));
								
							 }
							 else{
								 if (value == 8){
									 event.player.addChatComponentMessage(new ChatComponentText("Bring a lost Kid back to a Lolliman for a nice reward."));
									
								 }
								 else{
									 if (value == 9){
										 event.player.addChatComponentMessage(new ChatComponentText("Robot Ted thinks Robot Todd is a dirty chicken wing."));
										
									 }
								 
								 else{
									 if (value == 10){
										 event.player.addChatComponentMessage(new ChatComponentText("Sneaky Sal changes his prices. Check back for bargains."));
										
									 }
									 else{
										 if (value == 11){
											 event.player.addChatComponentMessage(new ChatComponentText("Power your HotDog with redstone for a fire attack!"));
											
										 }
										 else{
											 if (value == 12){
												 event.player.addChatComponentMessage(new ChatComponentText("You want money? Punch a Lawyer From Hell!"));
												
											 }
											 else{
												 if (value == 13){
													 event.player.addChatComponentMessage(new ChatComponentText("Equip your HotDogs with Redstone for fire attacks!"));
													
												 }
												 else{
													 if (value == 14){
														 event.player.addChatComponentMessage(new ChatComponentText("Guinea Pigs eat Wheat and Apples."));
														
													 }
													 else{
														 if (value == 15){
															 event.player.addChatComponentMessage(new ChatComponentText("A Floob Ship will spit out Floobs until it is destroyed."));
															
														 }
													 
													 else{
														 if (value == 16){
															 event.player.addChatComponentMessage(new ChatComponentText("Drop a BubbleScum 100 blocks for the MERCILESS achievement!"));
															
														 }
														 else{
															 if (value == 17){
																 event.player.addChatComponentMessage(new ChatComponentText("Throw a BubbleScum down a DigBug hole for a cookie fountain!"));
																
															 }
															 else{
																 if (value == 18){
																	 event.player.addChatComponentMessage(new ChatComponentText("Feed lots of cake to a Hunchback and he will stay loyal."));
																	
																 }
																 else{
																	 if (value == 19){
																		 event.player.addChatComponentMessage(new ChatComponentText("The longer you ride a RocketPony, the more tame it will be."));
																		
																	 }
																	 else{
																		 if (value == 20){
																			 event.player.addChatComponentMessage(new ChatComponentText("Visit Sneaky Sal for those hard to find items."));
																			
																		 }
																		 else{
																			 if (value == 21){
																				 event.player.addChatComponentMessage(new ChatComponentText("Hitting a Caveman will turn him/her evil!"));
																				
																			 }
																		 
																		 else{
																			 if (value == 22){
																				 event.player.addChatComponentMessage(new ChatComponentText("SNEAK KEY + RIGHT CLICK on creeps for info or to name them."));
																				
																			 }
																			 else{
																				 if (value == 23){
																					 event.player.addChatComponentMessage(new ChatComponentText("Give a level 20 Guinea Pig a diamond to build a Hotel!"));
																					
																				 }
																				 else{
																					 if (value == 24){
																						 event.player.addChatComponentMessage(new ChatComponentText("If you hear disco music - RUN!"));
																						
																					 }
																					 else{
																						 if (value == 25){
																							 event.player.addChatComponentMessage(new ChatComponentText("Raising your pets ATTACK skill will help them level faster."));
																							
																						 }
																						 else{
																							 if (value == 26){
																								 event.player.addChatComponentMessage(new ChatComponentText("Robot Ted and Todd will sometimes drop dirty chicken wings"));
																								
																							 }
																							 else{
																								 if (value == 27){
																									 event.player.addChatComponentMessage(new ChatComponentText("Killing a Lawyer may result in a Bum or Undead Lawyers"));
																									
																								 }
																								 else{
																									 if (value == 28){
																										 event.player.addChatComponentMessage(new ChatComponentText("Shrink a BigBaby down and put him in a jar to create a Schlump"));
																										
																									 }
																									 else{
																										 if (value == 29){
																											 event.player.addChatComponentMessage(new ChatComponentText("The older your Schlump gets, the more valuable gifts he gives!"));
																											
																										 }
																										 else{
																											 if (value == 30){
																												 event.player.addChatComponentMessage(new ChatComponentText("Do not throw eggs at Ponies! You have been warned!"));
																												
																											 }
																											 else{
																												 if (value == 31){
																													 event.player.addChatComponentMessage(new ChatComponentText("Some Prisoners are friendly and will reward you upon release!"));
																													
																												 }
																											 
																											 else{
																												 if (value == 32){
																													 event.player.addChatComponentMessage(new ChatComponentText("Some Prisoners are just evil and will attack you on sight!"));
																													
																												 }
																												 else{
																													 if (value == 33){
																														 event.player.addChatComponentMessage(new ChatComponentText("Evil Scientists will conduct experiments that sometimes backfire."));
																														
																													 }
																													 else{
																														 if (value == 34){
																															 event.player.addChatComponentMessage(new ChatComponentText("Your pet loses a level if resurrected with a LifeGem."));
																															
																														 }
																														 else{
																															 if (value == 35){
																																 event.player.addChatComponentMessage(new ChatComponentText("Sneaky Sal will sometimes sell goods at a discount."));
																																
																															 }
																														 					}
																													 					}
																												 					}
																											 					}
																											 				}
																										 				}
																									 				}
																								 				}
																							 				}
																						 				}
																					 				}
																				 				}
																			 				}
																		 				}
																		 			}
																	 			}
																 			}
															 			}
														 			}
													 			}
													 		}
												 		}
											 		}				 
										 		}
									 		}
								 		}
								 	}
							 	}
						 	}
					 	}
				 	}
			 	}
			 }
		 }
	 }
}