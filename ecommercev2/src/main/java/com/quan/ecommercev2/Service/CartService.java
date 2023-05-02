package com.quan.ecommercev2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quan.ecommercev2.Model.Cart;
import com.quan.ecommercev2.Model.CartItem;
import com.quan.ecommercev2.Model.Product;
import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Repo.CartItemRepository;
import com.quan.ecommercev2.Repo.CartRepository;
import com.quan.ecommercev2.Repo.ProductRepository;
import com.quan.ecommercev2.Repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    // public ResponseEntity<Cart> createCart(Integer userId) {
    // Optional<User> exsitingUser = userRepository.findById(userId);
    // Cart cart = new Cart();
    // cart.setUser(exsitingUser.orElseThrow(() -> new RuntimeException("USer not
    // found")));
    // Cart saveCart = cartRepository.save(cart);
    // return ResponseEntity.ok(saveCart);
    // }

    // public Cart getCartById(Long cartId) {
    // return cartRepository.findById(cartId).orElseThrow(() -> new
    // RuntimeException("Cart not found"));
    // }

    // public ResponseEntity<List<CartItem>> getCartItems(Cart cart) {
    // List<CartItem> items = cart.getItems();
    // return ResponseEntity.ok(items);
    // }

    // public ResponseEntity<String> addCartItem(long cartId, Long productId, int
    // quantity) {
    // Optional<Cart> optionalCart = cartRepository.findById(cartId);
    // Optional<Product> optionalProduct = productRepository.findById(productId);

    // if (optionalCart.isPresent() && optionalProduct.isPresent()) {
    // Cart cart = optionalCart.get();
    // List<CartItem> cartItems = cart.getItems();

    // Optional<CartItem> optionalCartItem = cartItems.stream()
    // .filter(item -> item.getProduct().getId().equals(productId))
    // .findFirst();

    // if (optionalCartItem.isPresent()) {
    // CartItem cartItem = optionalCartItem.get();
    // cartItem.setQuantity(cartItem.getQuantity() + quantity);
    // cartRepository.save(cart);
    // return ResponseEntity.ok("Item quantity updated");
    // } else {
    // CartItem newCartItem = new CartItem();
    // newCartItem.setProduct(optionalProduct.get());
    // newCartItem.setQuantity(quantity);
    // newCartItem.setCart(cart);
    // cartItems.add(newCartItem);
    // cartRepository.save(cart);
    // return ResponseEntity.ok("Item added to cart");
    // }
    // } else {
    // throw new RuntimeException("Invalid Product or Cart ID");
    // }
    // }

    // @Transactional
    // public ResponseEntity<String> removeCartItem(Long cartId, Long cartItemId) {
    // try {
    // Optional<Cart> savedCart = cartRepository.findById(cartId);
    // Optional<CartItem> savedCartItem = cartItemRepository.findById(cartItemId);
    // if (savedCart.isPresent() && savedCartItem.isPresent()) {
    // List<CartItem> cartItems = savedCart.get().getItems();
    // cartItems.remove(savedCartItem.get());

    // cartRepository.save(savedCart.get());
    // return ResponseEntity.ok("Cart item removed");
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // } catch (Exception exception) {
    // throw new RuntimeException("cart item or cart not found");
    // }
    // }

    // @Transactional
    // public ResponseEntity<String> clearCart(Long cartId) {
    // Optional<Cart> optionalCart = cartRepository.findById(cartId);
    // if (optionalCart.isPresent()) {
    // Cart cart = optionalCart.get();
    // List<CartItem> cartItems = cart.getItems();
    // cartItems.clear();
    // cartRepository.save(cart);
    // return ResponseEntity.ok("Cart cleared");
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // // public Optional<Cart> getCart(int userId) {
    // // return cartRepository.findByUserId(userId)
    // // .orElseGet(() -> createCart(userId));
    // // }
    // public ResponseEntity<Cart> getCart(int userId) {
    // Cart cart = cartRepository.findByUserId(userId)
    // .orElseGet(() -> createCart(userId));
    // return ResponseEntity.ok(cart);
    // }
    public Cart createCart(Integer userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        Cart cart = new Cart();
        cart.setUser(existingUser.orElseThrow(() -> new RuntimeException("User not found")));
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public List<CartItem> getCartItems(Cart cart) {
        return cart.getItems();
    }

    public String addCartItem(long cartId, Long productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalCart.isPresent() && optionalProduct.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cart.getItems();

            Optional<CartItem> optionalCartItem = cartItems.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartRepository.save(cart);
                return "Item quantity updated";
            } else {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(optionalProduct.get());
                newCartItem.setQuantity(quantity);
                newCartItem.setCart(cart);
                cartItems.add(newCartItem);
                cartRepository.save(cart);
                return "Item added to cart";
            }
        } else {
            throw new RuntimeException("Invalid Product or Cart ID");
        }
    }

    @Transactional
    public void removeCartItem(Long cartId, Long cartItemId) {
        try {
            Optional<Cart> savedCart = cartRepository.findById(cartId);
            Optional<CartItem> savedCartItem = cartItemRepository.findById(cartItemId);
            if (savedCart.isPresent() && savedCartItem.isPresent()) {
                List<CartItem> cartItems = savedCart.get().getItems();
                cartItems.remove(savedCartItem.get());

                cartRepository.save(savedCart.get());
            }

        } catch (Exception exception) {

            throw new RuntimeException("cart item or cart not found");
        }
    }

    @Transactional
    public void clearCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cart.getItems();
            cartItems.clear();
            cartRepository.save(cart);
        }
    }

    public Cart getCart(int userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createCart(userId));
    }

}
