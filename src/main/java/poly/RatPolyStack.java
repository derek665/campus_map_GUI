package poly;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
@SuppressWarnings("JdkObsolete")
public final class RatPolyStack implements Iterable<RatPoly> {

  /** Stack containing the RatPoly objects. */
  private final Stack<RatPoly> polys;

  // Abstraction Function:
  // Each element of a RatPolyStack, s, is mapped to the
  // corresponding element of polys.
  //
  // RepInvariant:
  // polys != null &&
  // forall i such that (0 <= i < polys.size(), polys.get(i) != null

  /** @spec.effects Constructs a new RatPolyStack, []. */
  public RatPolyStack() {
    polys = new Stack<RatPoly>();
    checkRep();
  }

  /**
   * Returns the number of RayPolys in this RatPolyStack.
   *
   * @return the size of this sequence
   */
  public int size() {
    checkRep();
    int size = 0;
    for (RatPoly rp : polys) {
      size++;
    }
    return size;
  }

  /**
   * Pushes a RatPoly onto the top of this.
   *
   * @param p the RatPoly to push onto this stack
   * @spec.requires p != null
   * @spec.modifies this
   * @spec.effects this_post = [p]:this
   */
  public void push(RatPoly p) {
    checkRep();
    polys.push(p);
    checkRep();
  }

  /**
   * Removes and returns the top RatPoly.
   *
   * @spec.requires {@code this.size() > 0}
   * @spec.modifies this
   * @spec.effects If this = [p]:S then this_post = S
   * @return p where this = [p]:S
   */
  public RatPoly pop() {
    checkRep();
    return polys.pop();
  }

  /**
   * Duplicates the top RatPoly on this.
   *
   * @spec.requires {@code this.size() > 0}
   * @spec.modifies this
   * @spec.effects If this = [p]:S then this_post = [p, p]:S
   */
  public void dup() {
    checkRep();
    RatPoly p = polys.pop();
    this.push(p);
    this.push(p);
  }

  /**
   * Swaps the top two elements of this.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S
   */
  public void swap() {
    checkRep();
    RatPoly p1 = polys.pop();
    RatPoly p2 = polys.pop();
    this.push(p1);
    this.push(p2);
  }

  /**
   * Clears the stack.
   *
   * @spec.modifies this
   * @spec.effects this_post = []
   */
  public void clear() {
    checkRep();
    while (!polys.isEmpty()) {
      polys.pop();
    }
  }

  /**
   * Returns the RatPoly that is 'index' elements from the top of the stack.
   *
   * @param index the index of the RatPoly to be retrieved
   * @spec.requires {@code index >= 0 && index < this.size()}
   * @return if this = S:[p]:T where S.size() = index, then returns p.
   */
  public RatPoly getNthFromTop(int index) {
    checkRep();
    Iterator<RatPoly> it = this.iterator();
    while (index > 0) {
      it.next();
      index--;
    }
    return it.next();
  }

  /**
   * Pops two elements off of the stack, adds them, and places the result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
   */
  public void add() {
    checkRep();
    RatPoly p1 = polys.pop();
    RatPoly p2 = polys.pop();
    this.push(p1.add(p2));
  }

  /**
   * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
   * result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
   */
  public void sub() {
    checkRep();
    RatPoly p1 = polys.pop();
    RatPoly p2 = polys.pop();
    this.push(p2.sub(p1));
  }

  /**
   * Pops two elements off of the stack, multiplies them, and places the result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
   */
  public void mul() {
    checkRep();
    RatPoly p1 = polys.pop();
    RatPoly p2 = polys.pop();
    this.push(p1.mul(p2));
  }

  /**
   * Divides the next from top poly by the top poly, pops both off the stack, and places the result
   * on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
   */
  public void div() {
    checkRep();
    RatPoly p1 = polys.pop();
    RatPoly p2 = polys.pop();
    this.push(p2.div(p1));
  }

  /**
   * Pops the top element off of the stack, differentiates it, and places the result on top of the
   * stack.
   *
   * @spec.requires {@code this.size() >= 1}
   * @spec.modifies this
   * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = derivative of p1
   */
  public void differentiate() {
    checkRep();
    this.push(polys.pop().differentiate());
  }

  /**
   * Pops the top element off of the stack, integrates it, and places the result on top of the
   * stack.
   *
   * @spec.requires {@code this.size() >= 1}
   * @spec.modifies this
   * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = indefinite integral of p1
   *     with integration constant 0
   */
  public void integrate() {
    checkRep();
    RatPoly p1 = polys.pop();
    this.push(p1.antiDifferentiate(RatNum.ZERO));
  }

  /**
   * Returns an iterator of the elements contained in the stack.
   *
   * @return an iterator of the elements contained in the stack in order from the bottom of the
   *     stack to the top of the stack
   */
  @Override
  public Iterator<RatPoly> iterator() {
    return polys.iterator();
  }

  /** Throws an exception if the representation invariant is violated. */
  private void checkRep() {
    assert (polys != null) : "polys should never be null.";
    
    for (RatPoly p : polys) {
        assert (p != null) : "polys should never contain a null element.";
    }
  }
}
